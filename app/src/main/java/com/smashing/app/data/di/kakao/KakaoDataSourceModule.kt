package com.smashing.app.data.di.kakao

import com.smashing.app.data.remote.datasource.api.KakaoRegionDataSource
import com.smashing.app.data.remote.datasource.impl.KakaoRegionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegionDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRegionDataSource(
        regionDataSourceImpl: KakaoRegionDataSourceImpl,
    ): KakaoRegionDataSource
}