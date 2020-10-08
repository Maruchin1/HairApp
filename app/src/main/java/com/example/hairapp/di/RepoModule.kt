package com.example.hairapp.di

import com.example.core.gateway.CareRepo
import com.example.core.gateway.ProductApplicationRepo
import com.example.core.gateway.ProductRepo
import com.example.data.MockCareRepo
import com.example.data.MockProductApplicationRepo
import com.example.data.RoomProductRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindProductRepo(
        roomProductRepo: RoomProductRepo
    ): ProductRepo

    @Binds
    abstract fun bindProductApplicationRepo(
        mockProductApplicationRepo: MockProductApplicationRepo
    ): ProductApplicationRepo

    @Binds
    abstract fun bindCareRepo(
        mockCareRepo: MockCareRepo
    ): CareRepo
}