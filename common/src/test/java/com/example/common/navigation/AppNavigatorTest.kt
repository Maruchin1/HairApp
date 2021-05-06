package com.example.common.navigation

import android.app.Activity
import android.content.Intent
import io.mockk.*
import org.junit.Test

class AppNavigatorTest {

    private val appNavigator by lazy {
        AppNavigator()
    }

    @Test
    fun toCareSchemaDetails() {
        val originActivity: Activity = mockk()
        val careSchemaId = 1
        val fakeIntent: Intent = mockk()
        mockkConstructor(Destination.CareSchemaDetails::class)
        every { anyConstructed<Destination.CareSchemaDetails>().makeIntent(any()) } returns fakeIntent
        justRun { originActivity.startActivity(any()) }

        appNavigator.toCareSchemaDetails(originActivity, careSchemaId)

        verify {
            originActivity.startActivity(fakeIntent)
        }
    }
}