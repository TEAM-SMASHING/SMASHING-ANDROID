package com.smashing.app.data.local.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smashing.app.core.local.datastore.di.UserDataStore
import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    @UserDataStore private val dataStore: DataStore<Preferences>,
): LocalUserDataSource {
    override suspend fun getUserId(): String? = dataStore.data
        .map { prefs ->
            prefs[USER_ID]
        }.firstOrNull()

    override suspend fun getUserNickName(): String? = dataStore.data
        .map { prefs ->
            prefs[USER_NICKNAME]
        }.firstOrNull()

    override suspend fun setUserInfo(userId: String, userNickname: String) {
        dataStore.edit { prefs ->
            prefs[USER_ID] = userId
            prefs[USER_NICKNAME] = userNickname
        }
    }

    override suspend fun clearUserInfo() {
        dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(USER_NICKNAME)
        }
    }

    companion object {
        private val USER_ID = stringPreferencesKey("USER_ID")
        private val USER_NICKNAME = stringPreferencesKey("USER_NICKNAME")
    }
}
