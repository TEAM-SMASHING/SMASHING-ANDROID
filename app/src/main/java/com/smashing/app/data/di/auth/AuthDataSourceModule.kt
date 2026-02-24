package com.smashing.app.data.di.auth

import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import com.smashing.app.data.local.datasource.impl.LocalTokenDataSourceImpl
import com.smashing.app.data.local.datasource.impl.LocalUserDataSourceImpl
import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalTokenDataSource(
        localTokenDataSourceImpl: LocalTokenDataSourceImpl
    ): LocalTokenDataSource

    @Binds
    @Singleton
    abstract fun bindLocalUserDataSource(
        localUserDataSourceImpl: LocalUserDataSourceImpl
    ): LocalUserDataSource
}
