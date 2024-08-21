package com.trifcdr.lifestylehub.data.storage

/**
 * Created by trifcdr.
 */
interface AppStorage {

    suspend fun saveKey(key: String): Boolean

    suspend fun getKey(): String
}