package com.example.corev2.room_database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideHairAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        HairAppDatabase::class.java,
        "hair-app-database.db"
    ).fallbackToDestructiveMigration().build()
}