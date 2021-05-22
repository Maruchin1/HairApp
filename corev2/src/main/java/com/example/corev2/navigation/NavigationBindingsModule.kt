package com.example.corev2.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigationBindingsModule {

    @Binds
    abstract fun bindNavigationService(
        navigationServiceImpl: NavigationServiceImpl
    ): NavigationService
}