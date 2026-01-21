package com.smashing.app.data.di.notification

import com.smashing.app.data.remote.datasource.api.NotificationRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.NotificationRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNotificationRemoteDataSource(
        notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl
    ): NotificationRemoteDataSource
}
