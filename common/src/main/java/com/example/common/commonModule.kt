package com.example.common

import com.example.common.modals.AppDialog
import com.example.common.modals.AppModal
import com.example.common.navigation.AppNavigator
import org.koin.dsl.module

val commonModule = module {
    single { AppNavigator() }

    factory { AppDialog() }

    factory { AppModal() }
}