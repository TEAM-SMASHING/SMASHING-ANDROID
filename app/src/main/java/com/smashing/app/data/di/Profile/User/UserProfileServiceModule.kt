package com.smashing.app.data.di.Profile.User

import com.smashing.app.data.remote.service.ReviewService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserProfileServiceModule {

    @Provides
    @Singleton
    fun provideUserProfileService(
        retrofit: Retrofit
    ): ReviewService = retrofit.create()
}
