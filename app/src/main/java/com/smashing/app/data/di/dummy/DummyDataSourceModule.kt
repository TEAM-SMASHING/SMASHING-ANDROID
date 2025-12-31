package com.smashing.app.data.di.dummy

import com.smashing.app.data.remote.datasource.api.DummyDataSource
import com.smashing.app.data.remote.datasource.impl.DummyDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DummyDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindDummyDataSource(
        dummyDataSourceImpl: DummyDataSourceImpl,
    ): DummyDataSource
}
