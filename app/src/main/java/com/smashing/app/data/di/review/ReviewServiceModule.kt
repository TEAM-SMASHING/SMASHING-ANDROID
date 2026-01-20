package com.smashing.app.data.di.review

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
object ReviewServiceModule {

    @Provides
    @Singleton
    fun provideReviewService(
        retrofit: Retrofit
    ): ReviewService = retrofit.create()
}
