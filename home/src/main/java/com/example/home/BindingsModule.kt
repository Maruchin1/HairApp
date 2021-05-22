package com.example.home

import com.example.corev2.navigation.HomeDestination
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BindingsModule {

    @Binds
    abstract fun bindHomeDestination(
        destinationImpl: DestinationImpl
    ): HomeDestination
}