package com.smashing.app.data.di.auth

import com.smashing.app.core.network.qualifier.NoAuth
import com.smashing.app.data.remote.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthServiceModule {

    @Provides
    @Singleton
    fun provideNoAuthService(
        @NoAuth retrofit: Retrofit
    ): AuthService = retrofit.create()
}
