package com.dev.shaumapps.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {

    val user_gender: Flow<String?>
        get() = context.dataStore.data.map { pref ->
            pref[USER_GENDER]
        }

    suspend fun saveUserGender(user_gender: String) {
        context.dataStore.edit { pref ->
            pref[USER_GENDER] = user_gender
        }
    }

    suspend fun clearUserGender() {
        context.dataStore.edit { pref ->
            pref.remove(USER_GENDER)
        }
    }

    companion object {
        private val USER_GENDER = stringPreferencesKey("user_gender")
    }
}