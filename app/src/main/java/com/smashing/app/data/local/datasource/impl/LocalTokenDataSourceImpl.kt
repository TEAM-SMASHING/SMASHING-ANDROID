package com.smashing.app.data.local.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smashing.app.core.local.datastore.di.TokenDataStore
import com.smashing.app.core.security.CryptoInterface
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocalTokenDataSourceImpl @Inject constructor(
    @param:TokenDataStore private val dataStore: DataStore<Preferences>,
    private val crypto: CryptoInterface,
) : LocalTokenDataSource {

    override suspend fun getAccessToken(): String? {
        val prefs = dataStore.data.first()
        val encoded = prefs[ENCRYPTED_ACCESS_TOKEN]
        return if (encoded != null) crypto.decrypt(encoded) else null
    }

    override suspend fun getRefreshToken(): String? {
        val prefs = dataStore.data.first()
        val encoded = prefs[ENCRYPTED_REFRESH_TOKEN]
        return if (encoded != null) crypto.decrypt(encoded) else null
    }

    override suspend fun setTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[ENCRYPTED_ACCESS_TOKEN] = crypto.encrypt(listOf(accessToken))
            prefs[ENCRYPTED_REFRESH_TOKEN] = crypto.encrypt(listOf(refreshToken))
            prefs.remove(ACCESS_TOKEN_IV)
            prefs.remove(REFRESH_TOKEN_IV)
        }
    }

    override suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(ENCRYPTED_ACCESS_TOKEN)
            prefs.remove(ENCRYPTED_REFRESH_TOKEN)
            prefs.remove(ACCESS_TOKEN_IV)
            prefs.remove(REFRESH_TOKEN_IV)
        }
    }

    companion object {
        private val ENCRYPTED_ACCESS_TOKEN = stringPreferencesKey("ENCRYPTED_ACCESS_TOKEN")
        private val ENCRYPTED_REFRESH_TOKEN = stringPreferencesKey("ENCRYPTED_REFRESH_TOKEN")
        private val ACCESS_TOKEN_IV = stringPreferencesKey("ACCESS_TOKEN_IV")
        private val REFRESH_TOKEN_IV = stringPreferencesKey("REFRESH_TOKEN_IV")
    }
}
