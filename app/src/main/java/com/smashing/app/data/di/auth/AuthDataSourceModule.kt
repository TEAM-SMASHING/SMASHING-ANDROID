package com.smashing.app.data.di.auth

import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.local.datasource.impl.LocalTokenDataSourceImpl
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
    abstract fun bindLocalTokenDataSource(
        localTokenDataSourceImpl: LocalTokenDataSourceImpl
    ): LocalTokenDataSource
}
