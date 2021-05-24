package com.example.product_form

import com.example.corev2.navigation.ProductFormDestination
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BindingsModule {

    @Binds
    abstract fun bindProductFormDestination(
        destinationImpl: DestinationImpl
    ): ProductFormDestination
}