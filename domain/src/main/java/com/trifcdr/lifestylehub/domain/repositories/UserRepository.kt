package com.trifcdr.lifestylehub.domain.repositories

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.user.User

/**
 * Created by trifcdr.
 */
interface UserRepository {

    suspend fun getRandomUser(): DomainResource<User>

}