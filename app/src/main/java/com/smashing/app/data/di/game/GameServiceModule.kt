package com.smashing.app.data.di.game

import com.smashing.app.data.remote.service.GameService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameServiceModule {

    @Provides
    @Singleton
    fun provideGameService(
        retrofit: Retrofit
    ): GameService = retrofit.create()
}
