package com.smashing.app.data.di.kakao

import com.smashing.app.data.repository.api.KakaoRegionRepository
import com.smashing.app.data.repository.impl.KakaoRegionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegionRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRegionRepository(
        regionRepositoryImpl: KakaoRegionRepositoryImpl,
    ): KakaoRegionRepository
}