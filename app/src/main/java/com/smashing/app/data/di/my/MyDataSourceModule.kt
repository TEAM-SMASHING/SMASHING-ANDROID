package com.smashing.app.data.di.my

import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.MyRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMyRemoteDataSource(
        myRemoteDataSourceImpl: MyRemoteDataSourceImpl
    ): MyRemoteDataSource
}