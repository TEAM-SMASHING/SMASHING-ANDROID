package com.smashing.app.data.di.addsports

import com.smashing.app.data.repository.api.AddSportsRepository
import com.smashing.app.data.repository.impl.AddSportsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AddSportsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAddSPortsRepository(
        impl: AddSportsRepositoryImpl
    ): AddSportsRepository
}
