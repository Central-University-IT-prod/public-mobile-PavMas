package com.trifcdr.lifestylehub.data.network.api.randomUser

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

class RandomUserApiImpl(
    private val httpClient: HttpClient
) : RandomUserApi {

    override suspend fun getRandomUser(): String {
        return try{
                httpClient.get<String> {
                    url("https://randomuser.me/api/")
                }

        }
        catch (_: Exception){
            return "error"
        }
    }
}