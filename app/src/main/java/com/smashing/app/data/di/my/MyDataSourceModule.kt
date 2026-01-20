package com.smashing.app.data.di.my

import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.MyRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MyDataSourceModule {
    @Binds
    @Singleton
    protected abstract fun bindMyRemoteDataSource(
        impl: MyRemoteDataSourceImpl
    ): MyRemoteDataSource
}
