package com.smashing.app.data.di.game

import com.smashing.app.data.remote.datasource.api.GameRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.GameRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindGameDataSource(
        gameDataSourceImpl: GameRemoteDataSourceImpl,
    ): GameRemoteDataSource
}
