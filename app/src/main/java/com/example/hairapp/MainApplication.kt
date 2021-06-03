package com.example.hairapp

import android.app.Application
import com.example.care_details.careDetailsModule
import com.example.care_schemas_list.careSchemasListModule
import com.example.cares_list.caresListModule
import com.example.corev2.corev2Module
import com.example.corev2.room_database.DatabaseInitializer
import com.example.edit_care_schema.editCareSchemaModule
import com.example.home.homeModule
import com.example.product_form.productFormModule
import com.example.products_list.productsListModule
import com.example.select_product.selectProductModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    private val modules = arrayOf(
        corev2Module,
        editCareSchemaModule,
        careSchemasListModule,
        homeModule,
        productFormModule,
        productsListModule,
        caresListModule,
        careDetailsModule,
        selectProductModule
    )

    private val databaseInitializer: DatabaseInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(*modules)
        }

        databaseInitializer.checkIfInitialized()
    }
}