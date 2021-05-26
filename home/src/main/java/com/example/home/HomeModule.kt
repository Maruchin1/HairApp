package com.example.home

import com.example.corev2.navigation.HomeDestination
import org.koin.dsl.module

val homeModule = module {
    factory<HomeDestination> {
        DestinationImpl()
    }
}