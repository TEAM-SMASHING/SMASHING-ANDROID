package com.smashing.app.data.di.review

import com.smashing.app.data.remote.datasource.api.ReviewRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.ReviewRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReviewDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindReviewRemoteDataSource(
        reviewRemoteDataSourceImpl: ReviewRemoteDataSourceImpl
    ): ReviewRemoteDataSource
}
