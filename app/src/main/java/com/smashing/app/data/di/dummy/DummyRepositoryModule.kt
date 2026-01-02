package com.smashing.app.data.di.dummy

import com.smashing.app.data.repository.api.DummyRepository
import com.smashing.app.data.repository.impl.DummyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DummyRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDummyRepository(
        dummyRepositoryImpl: DummyRepositoryImpl,
    ): DummyRepository
}
