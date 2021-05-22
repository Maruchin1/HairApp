package com.example.corev2.room

import com.example.corev2.DatabaseInitializer
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