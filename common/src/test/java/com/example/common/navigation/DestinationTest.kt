package com.example.common.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class DestinationTest {

    @Before
    fun before() {
        mockkConstructor(Intent::class)

        every {
            anyConstructed<Intent>().setClassName(any<Context>(), any())
        } answers { self as Intent }

        every {
            anyConstructed<Intent>().putExtra(any(), any<Int>())
        } answers { self as Intent }
    }

    @Test
    fun careSchemaDetails() {
        val careSchemaId = 1
        val originActivity: Activity = mockk()

        val destination = Destination.CareSchemaDetails(careSchemaId)
        val intent = destination.makeIntent(originActivity)

        verify {
            intent.setClassName(originActivity, Destination.CARE_SCHEMA_DETAILS_ACTIVITY)
            intent.putExtra(Destination.CARE_SCHEMA_ID, careSchemaId)
        }
    }
}