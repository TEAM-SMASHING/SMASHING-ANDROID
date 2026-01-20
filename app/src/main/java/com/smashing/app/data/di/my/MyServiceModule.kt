package com.smashing.app.data.di.my

import com.smashing.app.data.remote.service.MyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyServiceModule {

    @Provides
    @Singleton
    fun provideMyService(
        retrofit: Retrofit,
    ): MyService = retrofit.create()
}