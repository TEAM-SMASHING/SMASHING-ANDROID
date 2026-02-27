package com.smashing.app.data.local.datasource.impl

import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smashing.app.core.local.datastore.di.TokenDataStore
import com.smashing.app.core.security.CryptoInterface
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.security.GeneralSecurityException
import javax.inject.Inject

class LocalTokenDataSourceImpl @Inject constructor(
    @param:TokenDataStore private val dataStore: DataStore<Preferences>,
    private val crypto: CryptoInterface,
) : LocalTokenDataSource {

    override suspend fun getAccessToken(): String? = dataStore.data
        .map { prefs ->
            val cipherBase64 = prefs[ENCRYPTED_ACCESS_TOKEN]
            val ivBase64 = prefs[ACCESS_TOKEN_IV]
            if (cipherBase64 != null && ivBase64 != null) {
                try {
                    val ciphertext = Base64.decode(cipherBase64, Base64.NO_WRAP)
                    val iv = Base64.decode(ivBase64, Base64.NO_WRAP)
                    crypto.decrypt(ciphertext, iv)
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }.firstOrNull()

    override suspend fun getRefreshToken(): String? = dataStore.data
        .map { prefs ->
            val cipherBase64 = prefs[ENCRYPTED_REFRESH_TOKEN]
            val ivBase64 = prefs[REFRESH_TOKEN_IV]
            if (cipherBase64 != null && ivBase64 != null) {
                try {
                    val ciphertext = Base64.decode(cipherBase64, Base64.NO_WRAP)
                    val iv = Base64.decode(ivBase64, Base64.NO_WRAP)
                    crypto.decrypt(ciphertext, iv)
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }.firstOrNull()

    override suspend fun setTokens(accessToken: String, refreshToken: String) {
        val encryptedAccess = crypto.encrypt(listOf(accessToken))
        val encryptedRefresh = crypto.encrypt(listOf(refreshToken))
        dataStore.edit { prefs ->
            prefs[ENCRYPTED_ACCESS_TOKEN] =
                Base64.encodeToString(encryptedAccess.ciphertext, Base64.NO_WRAP)
            prefs[ACCESS_TOKEN_IV] = Base64.encodeToString(encryptedAccess.iv, Base64.NO_WRAP)
            prefs[ENCRYPTED_REFRESH_TOKEN] =
                Base64.encodeToString(encryptedRefresh.ciphertext, Base64.NO_WRAP)
            prefs[REFRESH_TOKEN_IV] = Base64.encodeToString(encryptedRefresh.iv, Base64.NO_WRAP)
        }
    }

    override suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(ENCRYPTED_ACCESS_TOKEN)
            prefs.remove(ACCESS_TOKEN_IV)
            prefs.remove(ENCRYPTED_REFRESH_TOKEN)
            prefs.remove(REFRESH_TOKEN_IV)
        }
    }

    companion object {
        private val ENCRYPTED_ACCESS_TOKEN = stringPreferencesKey("ENCRYPTED_ACCESS_TOKEN")
        private val ACCESS_TOKEN_IV = stringPreferencesKey("ACCESS_TOKEN_IV")
        private val ENCRYPTED_REFRESH_TOKEN = stringPreferencesKey("ENCRYPTED_REFRESH_TOKEN")
        private val REFRESH_TOKEN_IV = stringPreferencesKey("REFRESH_TOKEN_IV")
    }
}
