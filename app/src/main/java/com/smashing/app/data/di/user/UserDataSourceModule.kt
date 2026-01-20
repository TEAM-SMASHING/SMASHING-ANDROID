package com.smashing.app.data.di.user

import com.smashing.app.data.remote.datasource.api.UserRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource
}