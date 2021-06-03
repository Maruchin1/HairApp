package com.example.corev2

import androidx.room.Room
import com.example.corev2.data_store.InitializationCompletionStore
import com.example.corev2.room_database.DatabaseInitializer
import com.example.corev2.room_database.DatabaseInitializerImpl
import com.example.corev2.room_database.HairAppDatabase
import com.example.corev2.service.ClockService
import com.example.corev2.ui.DialogService
import com.example.corev2.ui.ImagePickerService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val corev2Module = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            HairAppDatabase::class.java,
            "hair-app-database.db"
        ).fallbackToDestructiveMigration().build()
    }
    factory<DatabaseInitializer> {
        DatabaseInitializerImpl(androidContext(), get(), get(), get())
    }
    single {
        InitializationCompletionStore(androidContext())
    }
    factory {
        get<HairAppDatabase>().careSchemaDao()
    }
    factory {
        get<HairAppDatabase>().careSchemaStepDao()
    }
    factory {
        get<HairAppDatabase>().productDao()
    }
    factory {
        get<HairAppDatabase>().careDao()
    }
    factory {
        get<HairAppDatabase>().carePhotoDao()
    }
    factory {
        get<HairAppDatabase>().careStepDao()
    }
    factory {
        DialogService(get())
    }
    factory {
        ImagePickerService()
    }
    factory {
        ClockService()
    }
}