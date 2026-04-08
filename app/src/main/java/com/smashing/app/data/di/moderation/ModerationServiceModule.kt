package com.smashing.app.data.di.moderation

import com.smashing.app.data.remote.service.ModerationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModerationServiceModule {

    @Provides
    @Singleton
    fun provideModerationService(
        retrofit: Retrofit,
    ): ModerationService = retrofit.create()
}
