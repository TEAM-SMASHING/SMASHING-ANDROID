package com.smashing.app.core.local.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val TOKEN_PREFERENCE_NAME = "token_preference"
    private const val USER_PREFERENCE_NAME = "user_preference"

    private val Context.tokenDataStore by preferencesDataStore(TOKEN_PREFERENCE_NAME)
    private val Context.userDataStore by preferencesDataStore(USER_PREFERENCE_NAME)

    @TokenDataStore
    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.tokenDataStore

    @UserDataStore
    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.userDataStore
}
