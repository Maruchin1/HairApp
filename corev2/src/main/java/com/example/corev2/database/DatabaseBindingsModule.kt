package com.example.corev2.database

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseBindingsModule {

    @Binds
    abstract fun bindDatabaseInitializer(
        databaseInitializerImpl: DatabaseInitializerImpl
    ): DatabaseInitializer
}