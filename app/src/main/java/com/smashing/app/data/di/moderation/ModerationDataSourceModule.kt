package com.smashing.app.data.di.moderation

import com.smashing.app.data.remote.datasource.api.ModerationRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.ModerationRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModerationDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindModerationRemoteDataSource(
        impl: ModerationRemoteDataSourceImpl,
    ): ModerationRemoteDataSource
}
