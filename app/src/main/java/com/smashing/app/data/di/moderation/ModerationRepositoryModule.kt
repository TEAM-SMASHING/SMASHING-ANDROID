package com.smashing.app.data.di.moderation

import com.smashing.app.data.repository.api.ModerationRepository
import com.smashing.app.data.repository.impl.ModerationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModerationRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindModerationRepository(
        impl: ModerationRepositoryImpl,
    ): ModerationRepository
}
