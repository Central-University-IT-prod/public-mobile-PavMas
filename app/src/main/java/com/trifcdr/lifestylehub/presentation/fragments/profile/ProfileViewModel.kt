package com.trifcdr.lifestylehub.presentation.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trifcdr.lifestylehub.authorization.AuthResource
import com.trifcdr.lifestylehub.authorization.AuthorizationApi
import com.trifcdr.lifestylehub.authorization.database.model.AuthUser
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.usecase.GetRandomUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by trifcdr.
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authorizationApi: AuthorizationApi,
    private val getRandomUserUseCase: GetRandomUserUseCase
) : ViewModel() {

    var user: AuthUser? = null

    fun getCurrentUser() = flow{
        try{
            val userResult = authorizationApi.getUser()
            if (userResult is AuthResource.Success) user = userResult.result
            emit(userResult)
        }
        catch (e: Exception){
            emit(AuthResource.Failure(e))
        }
    }

    fun deleteCurrentUser() = viewModelScope.launch {
        authorizationApi.logOut()
    }

    fun getRandomUser() = flow {
        try {
            emit(getRandomUserUseCase.execute())
        }
        catch (e: Exception){
            emit(DomainResource.Failure(e))
        }
    }

    fun authenticateUser(login: String, password: String) = flow {
        try {
            val userResult = authorizationApi.authorized(login, password)
            if (userResult is AuthResource.Success) user = userResult.result
            emit(userResult)
        }
        catch (e: Exception){
            emit(AuthResource.Failure(e))
        }
    }


    fun registerUser(newUser: AuthUser) = flow {
        try {
            val userResult =  authorizationApi.register(newUser)
            if (userResult is AuthResource.Success) user = userResult.result
            emit(userResult)
        }
        catch (e: Exception){
            emit(AuthResource.Failure(e))
        }
    }

}