package com.smashing.app.data.di.profile.myprofile

import com.smashing.app.data.remote.service.MyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object MyServiceModule {
    @Provides
    @Singleton
    fun provideMyService(retrofit: Retrofit): MyService = retrofit.create()
}
