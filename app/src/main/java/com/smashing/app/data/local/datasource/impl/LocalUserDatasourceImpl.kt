package com.smashing.app.data.local.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smashing.app.core.local.datastore.di.UserDataStore
import com.smashing.app.data.local.datasource.api.LocalUserDatasource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDatasourceImpl @Inject constructor(
    @UserDataStore private val dataStore: DataStore<Preferences>,
): LocalUserDatasource {
    override suspend fun getUserId(): String? = dataStore.data
        .map { prefs ->
            prefs[USER_ID]
        }.firstOrNull()

    override suspend fun setUserId(userId: String) {
        dataStore.edit { prefs ->
            prefs[USER_ID] = userId
        }
    }

    override suspend fun clearUserId() {
        dataStore.edit { prefs ->
            prefs.remove(USER_ID)
        }
    }

    companion object {
        private val USER_ID = stringPreferencesKey("USER_ID")
    }
}
