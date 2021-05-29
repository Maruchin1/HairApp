package com.example.home

import com.example.corev2.navigation.HomeDestination
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    factory(named(HomeDestination.ACTIVITY)) {
        HomeActivity::class.java
    }
}