package com.example.hairapp.di

import com.example.core.gateway.ProductRepo
import com.example.data.MockProductRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
abstract class MockDataModule {

    @Binds
    abstract fun bindProductRepo(mockProductRepo: MockProductRepo): ProductRepo
}