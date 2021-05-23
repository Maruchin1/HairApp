package com.example.corev2

import com.example.corev2.room_database.DatabaseInitializer
import com.example.corev2.room_database.DatabaseInitializerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BindingsModule {

    @Binds
    abstract fun bindDatabaseInitializer(
        databaseInitializerImpl: DatabaseInitializerImpl
    ): DatabaseInitializer
}