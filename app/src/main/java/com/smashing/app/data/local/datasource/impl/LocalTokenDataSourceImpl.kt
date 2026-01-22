package com.smashing.app.data.local.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smashing.app.core.local.datastore.di.TokenDataStore
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalTokenDataSourceImpl @Inject constructor(
    @TokenDataStore private val dataStore: DataStore<Preferences>,
) : LocalTokenDataSource {

    override val accessTokenFlow: Flow<String?> = dataStore.data
        .map { prefs -> prefs[ACCESS_TOKEN] }

    override suspend fun getAccessToken(): String? = dataStore.data
        .map { prefs ->
            prefs[ACCESS_TOKEN]
        }.firstOrNull()

    override suspend fun getRefreshToken(): String? = dataStore.data
        .map { prefs ->
            prefs[REFRESH_TOKEN]
        }.firstOrNull()

    override suspend fun setTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = accessToken
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }
}
