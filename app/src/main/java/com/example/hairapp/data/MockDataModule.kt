package com.example.hairapp.data

import com.example.hairapp.repository.ProductRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class MockDataModule {

    @Binds
    abstract fun bindProductRepo(mockProductRepo: MockProductRepo): ProductRepo
}