package com.example.data

import androidx.room.Room
import com.example.core.gateway.CareRepo
import com.example.core.gateway.ApplicationRepo
import com.example.core.gateway.ProductRepo
import com.example.data.repo.RoomApplicationRepo
import com.example.data.repo.RoomCareRepo
import com.example.data.repo.RoomProductRepo
import com.example.data.room.Mapper
import com.example.data.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    // Database
    single {
        Room.databaseBuilder(androidContext(), RoomDatabase::class.java, "local-database")
            .build()
    }

    // Dao
    single { get<RoomDatabase>().applicationDao() }
    single { get<RoomDatabase>().careDao() }
    single { get<RoomDatabase>().carePhotoDao() }
    single { get<RoomDatabase>().careProductDao() }
    single { get<RoomDatabase>().productApplicationDao() }
    single { get<RoomDatabase>().productDao() }

    // Mapper
    single {
        Mapper(
            applicationDao = get(),
            careDao = get(),
            productDao = get()
        )
    }

    // Repo
    single<ProductRepo> {
        RoomProductRepo(
            mapper = get(),
            productDao = get(),
            productApplicationDao = get()
        )
    }
    single<CareRepo> {
        RoomCareRepo(
            mapper = get(),
            careDao = get(),
            productDao = get()
        )
    }
    single<ApplicationRepo> {
        RoomApplicationRepo(
            mapper = get(),
            applicationDao = get()
        )
    }

}