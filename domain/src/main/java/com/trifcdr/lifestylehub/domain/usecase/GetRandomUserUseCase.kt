package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.user.User
import com.trifcdr.lifestylehub.domain.repositories.UserRepository

/**
 * Created by trifcdr.
 */
class GetRandomUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(): DomainResource<User>{
        return userRepository.getRandomUser()
    }

}