package com.smashing.app.data.di.region

import com.smashing.app.data.remote.datasource.api.KakaoRegionDataSource
import com.smashing.app.data.remote.datasource.api.RegionRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.KakaoRegionDataSourceImpl
import com.smashing.app.data.remote.datasource.impl.RegionRemoteDataSourceImpl
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
    abstract fun bindRegionRemoteDataSource(
        regionRemoteDataSourceImpl: RegionRemoteDataSourceImpl
    ): RegionRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindKakaoRegionDataSource(
        kakaoRegionDataSourceImpl: KakaoRegionDataSourceImpl
    ): KakaoRegionDataSource
}