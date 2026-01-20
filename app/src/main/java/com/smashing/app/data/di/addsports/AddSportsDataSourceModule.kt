package com.smashing.app.data.di.addsports

import com.smashing.app.data.remote.datasource.api.AddSportsRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.AddSportsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AddSportsDataSourceModule {
    @Binds
    @Singleton
    protected abstract fun bindAddSportsRemoteDataSource(
        impl: AddSportsRemoteDataSourceImpl
    ): AddSportsRemoteDataSource
}
