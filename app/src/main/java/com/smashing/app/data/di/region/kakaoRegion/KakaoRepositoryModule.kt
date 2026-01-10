package com.smashing.app.data.di.region.kakaoRegion

import com.smashing.app.data.repository.api.RegionRepository
import com.smashing.app.data.repository.impl.RegionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoRegionRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRegionRepository(
        regionRepositoryImpl: RegionRepositoryImpl,
    ): RegionRepository
}