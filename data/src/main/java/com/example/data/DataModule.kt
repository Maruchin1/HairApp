package com.example.data

import androidx.room.Room
import com.example.core.gateway.AppPreferences
import com.example.core.gateway.CareRepo
import com.example.core.gateway.CareSchemaRepo
import com.example.core.gateway.ProductRepo
import com.example.data.repo.DataStoreAppPreferences
import com.example.data.repo.RoomCareRepo
import com.example.data.repo.RoomCareSchemaRepo
import com.example.data.repo.RoomProductRepo
import com.example.data.room.Mapper
import com.example.data.room.RoomDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    // Database

    single {
        Room.databaseBuilder(androidContext(), RoomDatabase::class.java, "local-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    // Dao

    single { get<RoomDatabase>().careDao() }

    single { get<RoomDatabase>().carePhotoDao() }

    single { get<RoomDatabase>().careSchemaDao() }

    single { get<RoomDatabase>().careSchemaStepDao() }

    single { get<RoomDatabase>().careProductDao() }

    single { get<RoomDatabase>().productDao() }

    // Mapper

    single {
        Mapper(
            productDao = get()
        )
    }

    // Repo

    single<CareRepo> {
        RoomCareRepo(
            mapper = get(),
            careDao = get(),
            carePhotoDao = get(),
            careStepDao = get()
        )
    }

    single<CareSchemaRepo> {
        RoomCareSchemaRepo(
            context = androidContext(),
            mapper = get(),
            careSchemaDao = get(),
            careSchemaStepDao = get()
        )
    }

    single<ProductRepo> {
        RoomProductRepo(
            mapper = get(),
            productDao = get()
        )
    }

    single<AppPreferences> {
        DataStoreAppPreferences(
            context = androidApplication()
        )
    }

}