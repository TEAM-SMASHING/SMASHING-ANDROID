package com.smashing.app.data.di.region

import com.smashing.app.core.network.di.Kakao
import com.smashing.app.data.remote.service.KakaoRegionService
import com.smashing.app.data.remote.service.RegionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegionServiceModule {

    @Provides
    @Singleton
    fun provideRegionService(
        retrofit: Retrofit,
    ): RegionService = retrofit.create()

    @Provides
    @Singleton
    fun provideKakaoRegionService(
        @Kakao retrofit: Retrofit,
    ): KakaoRegionService = retrofit.create()
}
