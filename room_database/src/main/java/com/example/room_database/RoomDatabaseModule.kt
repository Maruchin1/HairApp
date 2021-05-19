package com.example.room_database

import androidx.room.Room
import com.example.room_database.repositories.RoomCareSchemaRepo
import com.example.room_database.transactions.AddNewCareSchemaTransaction
import com.example.room_database.transactions.UpdateCareSchemaTransaction
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomDatabaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            HairAppDatabase::class.java,
            "hair-app-database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Dao
    factory { get<HairAppDatabase>().careSchemaDao() }
    factory { get<HairAppDatabase>().careSchemaStepDao() }

    // Transactions
    factory {
        AddNewCareSchemaTransaction(
            careSchemaDao = get(),
            careSchemaStepDao = get()
        )
    }
    factory {
        UpdateCareSchemaTransaction(
            careSchemaDao = get(),
            careSchemaStepDao = get()
        )
    }

    // Repo
    factory {
        RoomCareSchemaRepo(
            addNewCareSchemaTransaction = get(),
            updateCareSchemaTransaction = get()
        )
    }
}