package com.smashing.app.core.security.di

import com.smashing.app.core.security.CryptoInterface
import com.smashing.app.core.security.CryptoManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {

    @Binds
    @Singleton
    abstract fun bindCryptoInterface(cryptoManager: CryptoManager): CryptoInterface
}