package com.trifcdr.lifestylehub.data.network.api.randomUser

import kotlinx.serialization.json.Json

/**
 * Created by trifcdr.
 */
interface RandomUserApi {


    suspend fun getRandomUser(): String
}