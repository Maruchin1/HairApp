package com.example.common

import com.example.common.navigation.AppNavigator
import org.koin.dsl.module

val commonModule = module {
    single { AppNavigator() }
}