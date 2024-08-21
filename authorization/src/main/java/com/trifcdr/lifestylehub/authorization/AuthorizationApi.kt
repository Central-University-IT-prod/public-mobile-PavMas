package com.trifcdr.lifestylehub.authorization

import com.trifcdr.lifestylehub.authorization.database.model.AuthUser

/**
 * Created by trifcdr.
 */
interface AuthorizationApi {

    //log in by login and password
    suspend fun authorized(login: String, password: String): AuthResource<AuthUser>

    //register user and add him into database
    suspend fun register(user: AuthUser): AuthResource<AuthUser>

    //get an already authorized user
    suspend fun getUser(): AuthResource<AuthUser>

    //log out already authorized user
    suspend fun logOut()
}