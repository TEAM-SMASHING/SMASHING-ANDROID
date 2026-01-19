package com.smashing.app.data.di.profile.myprofile

import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.impl.MyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMyRepository(
        impl: MyRepositoryImpl
    ): MyRepository
}
