package com.trifcdr.lifestylehub.data.repository

import com.trifcdr.lifestylehub.data.network.api.randomUser.RandomUserApi
import com.trifcdr.lifestylehub.data.mappers.mapToDomainUser
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.user.User
import com.trifcdr.lifestylehub.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userApi: RandomUserApi
) : UserRepository {

    override suspend fun getRandomUser(): DomainResource<User> {
        return DomainResource.Success(mapToDomainUser(userApi.getRandomUser()))

    }
}