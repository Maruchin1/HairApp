package com.example.care_details.use_case

import arrow.core.handleError
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteCareStepUseCaseTest {
    private val actions = mockk<DeleteCareStepUseCase.Actions>()
    private val careStepDao = mockk<CareStepDao>()
    private val deleteCareStepUseCase by lazy {
        DeleteCareStepUseCase(actions, careStepDao)
    }

    private val careStepToDelete = CareStep(
        id = 1,
        productType = Product.Type.OIL,
        order = 4,
        productId = null,
        careId = 1
    )

    @Before
    fun before() {
        coEvery { actions.confirmCareStepDeletion() } returns true
        coJustRun { careStepDao.delete(*anyVararg()) }
    }

    @Test
    fun deletionNotConfirmed() = runBlocking {
        coEvery { actions.confirmCareStepDeletion() } returns false

        val result = deleteCareStepUseCase(careStepToDelete)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(DeleteCareStepUseCase.Fail.DeletionNotConfirmed::class.java)
        }
        coVerify(exactly = 0) {
            careStepDao.delete(*anyVararg())
        }
    }

    @Test
    fun successfullyDeleted() = runBlocking {
        val result = deleteCareStepUseCase(careStepToDelete)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careStepDao.delete(careStepToDelete)
        }
    }
}