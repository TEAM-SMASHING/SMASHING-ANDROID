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
        return encoded?.let { crypto.decrypt(it).getOrNull() }
    }

    override suspend fun getRefreshToken(): String? {
        val prefs = dataStore.data.first()
        val encoded = prefs[ENCRYPTED_REFRESH_TOKEN]
        return encoded?.let { crypto.decrypt(it).getOrNull() }
    }

    override suspend fun setTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            crypto.encrypt(accessToken).getOrNull()?.let { encryptedAccess ->
                prefs[ENCRYPTED_ACCESS_TOKEN] = encryptedAccess
            }
            crypto.encrypt(refreshToken).getOrNull()?.let { encryptedRefresh ->
                prefs[ENCRYPTED_REFRESH_TOKEN] = encryptedRefresh
            }
        }
    }

    override suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(ENCRYPTED_ACCESS_TOKEN)
            prefs.remove(ENCRYPTED_REFRESH_TOKEN)
        }
    }

    companion object {
        private val ENCRYPTED_ACCESS_TOKEN = stringPreferencesKey("ENCRYPTED_ACCESS_TOKEN")
        private val ENCRYPTED_REFRESH_TOKEN = stringPreferencesKey("ENCRYPTED_REFRESH_TOKEN")
    }
}
