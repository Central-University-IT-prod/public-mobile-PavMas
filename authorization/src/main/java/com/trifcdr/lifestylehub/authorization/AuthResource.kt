package com.trifcdr.lifestylehub.authorization

/**
 * Created by trifcdr.
 */
sealed class AuthResource<out R> {

    data class Success<out R>(val result: R): AuthResource<R>()
    data object UserDoesNotExist: AuthResource<Nothing>()
    data object UserAlreadyRegister: AuthResource<Nothing>()
    data object Forbidden: AuthResource<Nothing>()
    data class Failure(val exception: Exception): AuthResource<Nothing>()
    data object Loading : AuthResource<Nothing>()
    data object NotAuthorized : AuthResource<Nothing>()

}