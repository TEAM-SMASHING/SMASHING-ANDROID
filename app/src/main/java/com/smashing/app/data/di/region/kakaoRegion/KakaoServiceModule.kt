package com.smashing.app.data.di.region.kakaoRegion

import com.smashing.app.core.network.qualifier.Kakao
import com.smashing.app.data.remote.service.KakaoRegionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoServiceModule {
    @Provides
    @Singleton
    fun provideKakaoService(
        @Kakao retrofit: Retrofit,
    ): KakaoRegionService = retrofit.create()
}