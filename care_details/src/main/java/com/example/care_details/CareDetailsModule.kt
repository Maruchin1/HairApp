package com.example.care_details

import com.example.care_details.components.CareDetailsActivity
import com.example.corev2.navigation.CareDetailsDestination
import org.koin.core.qualifier.named
import org.koin.dsl.module

val careDetailsModule = module {
    factory(named(CareDetailsDestination.ACTIVITY)) {
        CareDetailsActivity::class.java
    }
}