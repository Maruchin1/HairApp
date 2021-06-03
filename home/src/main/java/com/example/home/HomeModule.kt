package com.example.home

import com.example.navigation.DestinationType
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    factory(named(DestinationType.HOME)) {
        HomeActivity::class.java
    }
}