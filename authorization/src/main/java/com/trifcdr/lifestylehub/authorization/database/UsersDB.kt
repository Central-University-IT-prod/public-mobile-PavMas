package com.trifcdr.lifestylehub.authorization.database

import com.trifcdr.lifestylehub.authorization.database.model.AuthUser

/**
 * Created by trifcdr.
 */
interface UsersDB {

    suspend fun getUserByLogin(login: String): AuthUser?

    suspend fun insertUser(user: AuthUser): AuthUser

    suspend fun deleteUser(user: AuthUser)
}