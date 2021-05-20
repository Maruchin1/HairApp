package com.example.corev2.di

import android.content.Context
import androidx.room.Room
import com.example.corev2.HairAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideHairAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        HairAppDatabase::class.java,
        "hair-app-database"
    ).build()

    @Provides
    fun provideCareSchemaDao(
        database: HairAppDatabase
    ) = database.careSchemaDao()

    @Provides
    fun provideCareSchemaStepDao(
        database: HairAppDatabase
    ) = database.careSchemaStepDao()
}