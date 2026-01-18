package com.smashing.app.data.di.Profile.User

import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.data.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserProfileRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}
