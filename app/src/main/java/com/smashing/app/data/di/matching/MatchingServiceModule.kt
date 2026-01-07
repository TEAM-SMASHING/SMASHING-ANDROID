package com.smashing.app.data.di.matching

import com.smashing.app.data.remote.service.MatchingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MatchingServiceModule {
    @Provides
    @Singleton
    fun provideMatchingService(retrofit: Retrofit): MatchingService = retrofit.create()
}
