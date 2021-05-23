package com.example.corev2.dao

import com.example.corev2.room_database.HairAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideCareSchemaDao(
        database: HairAppDatabase
    ) = database.careSchemaDao()

    @Provides
    fun provideCareSchemaStepDao(
        database: HairAppDatabase
    ) = database.careSchemaStepDao()
}