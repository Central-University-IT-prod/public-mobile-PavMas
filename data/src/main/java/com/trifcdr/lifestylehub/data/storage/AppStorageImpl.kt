package com.trifcdr.lifestylehub.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("user_preferences")

class AppStorageImpl(context: Context) : AppStorage {


    private val dataStore = context.dataStore




    override suspend fun saveKey(key: String): Boolean {
        val dataStoreKey = stringPreferencesKey(OAUTH_TOKEN_KEY)
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { settings ->
                settings[dataStoreKey] = key
            }
        }.join()
         return true
    }

    override suspend fun getKey(): String {
        var res: String? = null
        CoroutineScope(Dispatchers.IO).launch {
            val dataStoreKey = stringPreferencesKey(OAUTH_TOKEN_KEY)
            val prefs = dataStore.data.first()
            res = prefs[dataStoreKey] ?: return@launch
        }.join()

        return res ?: DEFAULT_TOKEN_VALUE
    }

    companion object{
        private const val OAUTH_TOKEN_KEY = "api_key"
        private const val DEFAULT_TOKEN_VALUE = "empty"
    }
}