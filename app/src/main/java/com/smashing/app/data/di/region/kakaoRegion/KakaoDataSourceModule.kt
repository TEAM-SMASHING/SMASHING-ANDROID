package com.smashing.app.data.di.region.kakaoRegion

import com.smashing.app.data.remote.datasource.api.KakaoRegionDataSource
import com.smashing.app.data.remote.datasource.impl.KakaoRegionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoRegionDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRegionDataSource(
        regionDataSourceImpl: KakaoRegionDataSourceImpl,
    ): KakaoRegionDataSource
}