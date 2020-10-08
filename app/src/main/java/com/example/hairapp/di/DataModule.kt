package com.example.hairapp.di

import android.content.Context
import androidx.room.Room
import com.example.core.gateway.ProductRepo
import com.example.data.RoomProductRepo
import com.example.data.dao.*
import com.example.data.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): RoomDatabase {
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "local-database"
        ).build()
    }

    @Provides
    fun provideApplicationDao(roomDatabase: RoomDatabase): ApplicationDao {
        return roomDatabase.applicationDao()
    }

    @Provides
    fun provideCareDao(roomDatabase: RoomDatabase): CareDao {
        return roomDatabase.careDao()
    }

    @Provides
    fun provideCarePhotoDao(roomDatabase: RoomDatabase): CarePhotoDao {
        return roomDatabase.carePhotoDao()
    }

    @Provides
    fun provideCareProductDao(roomDatabase: RoomDatabase): CareProductDao {
        return roomDatabase.careProductDao()
    }

    @Provides
    fun provideProductApplicationDao(roomDatabase: RoomDatabase): ProductApplicationDao {
        return roomDatabase.productApplicationDao()
    }

    @Provides
    fun provideProductDao(roomDatabase: RoomDatabase): ProductDao {
        return roomDatabase.productDao()
    }
}