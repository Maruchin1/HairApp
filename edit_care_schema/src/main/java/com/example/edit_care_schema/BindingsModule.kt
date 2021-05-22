package com.example.edit_care_schema

import com.example.corev2.navigation.EditCareSchemaDestination
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BindingsModule {

    @Binds
    abstract fun bindEditCareSchemaDestination(
        destinationImpl: DestinationImpl
    ): EditCareSchemaDestination
}