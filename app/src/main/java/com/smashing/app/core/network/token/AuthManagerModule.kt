package com.smashing.app.core.network.token

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthManagerModule {

    @Binds
    @Singleton
    abstract fun bindAuthManager(
        authManager: AuthManagerImpl
    ): AuthManager
}
