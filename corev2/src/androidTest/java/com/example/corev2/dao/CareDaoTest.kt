package com.example.corev2.dao

import com.example.corev2.entities.Care
import com.example.corev2.entities.CarePhoto
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.relations.CareWithStepsAndPhotos
import com.example.testing.rules.DatabaseTestRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class CareDaoTest {

    @get:Rule
    val databaseTestRule = DatabaseTestRule()

    private val careDao by lazy { databaseTestRule.db.careDao() }
    private val careStepDao by lazy { databaseTestRule.db.careStepDao() }
    private val carePhotoDao by lazy { databaseTestRule.db.carePhotoDao() }
    private val productsDao by lazy { databaseTestRule.db.productDao() }
    private val today = LocalDate.now()

    private val care = Care(
        schemaName = "OMO",
        date = today,
        notes = "Lorem ipsum"
    )
    private val steps = arrayOf(
        CareStep(
            productType = Product.Type.CONDITIONER,
            order = 0,
            productId = 1,
            careId = 1
        ),
        CareStep(
            productType = Product.Type.SHAMPOO,
            order = 1,
            productId = 2,
            careId = 1
        ),
        CareStep(
            productType = Product.Type.CONDITIONER,
            order = 2,
            productId = null,
            careId = 1
        )
    )
    private val photos = arrayOf(
        CarePhoto(data = "abc", careId = 1)
    )
    private val products = arrayOf(
        Product(
            name = "Odżywka",
            applications = setOf(Product.Application.CONDITIONER)
        ),
        Product(
            name = "Szampon",
            applications = setOf(Product.Application.STRONG_SHAMPOO)
        )
    )

    @Before
    fun before() {
        runBlocking {
            careDao.insert(care)
            careStepDao.insert(*steps)
            carePhotoDao.insert(*photos)
            productsDao.insert(*products)
        }
    }

    @Test
    fun getCareWithStepsAndPhotosById() = runBlocking {
        val result = careDao.getById(1).firstOrNull()

        assertThat(result).isEqualTo(
            CareWithStepsAndPhotos(
                care = care.copy(id = 1),
                steps = listOf(
                    CareStepWithProduct(
                        careStep = steps[0].copy(id = 1),
                        product = products[0].copy(id = 1)
                    ),
                    CareStepWithProduct(
                        careStep = steps[1].copy(id = 2),
                        product = products[1].copy(id = 2)
                    ),
                    CareStepWithProduct(
                        careStep = steps[2].copy(id = 3),
                        product = null
                    )
                ),
                photos = listOf(
                    photos[0].copy(id = 1)
                )
            )
        )
    }
}