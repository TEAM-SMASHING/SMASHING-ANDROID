package com.smashing.app.data.di.auth

import com.smashing.app.data.remote.service.DummyService
import com.smashing.app.data.remote.service.KakaoLoginService
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
    fun provideAuthService(retrofit: Retrofit): KakaoLoginService = retrofit.create()
}
