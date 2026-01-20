package com.smashing.app.data.di.addsports

import com.smashing.app.data.remote.service.AddSportsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddSportsServiceModule {
    @Provides
    @Singleton
    fun provideAddSportsService(retrofit: Retrofit): AddSportsService = retrofit.create()
}
