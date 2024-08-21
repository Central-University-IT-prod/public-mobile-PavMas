package com.trifcdr.lifestylehub.authorization.database

import android.content.Context
import com.trifcdr.lifestylehub.authorization.database.model.AuthUser
import com.trifcdr.lifestylehub.authorization.database.room.UsersDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersDBImpl(
    private val context: Context
) : UsersDB {

    private val userDatabase by lazy { UsersDatabase.getInstance(context = context) }

    override suspend fun getUserByLogin(login: String): AuthUser? {
        var user: AuthUser? = null
        CoroutineScope(Dispatchers.IO).launch {
            user = userDatabase.getUsersDao().getUserByLogin(login)
        }.join()
        return user
    }

    override suspend fun insertUser(user: AuthUser): AuthUser{
        CoroutineScope(Dispatchers.IO).launch {
            userDatabase.getUsersDao().insertUser(user)
        }.join()
        return user
    }

    override suspend fun deleteUser(user: AuthUser) {
        CoroutineScope(Dispatchers.IO).launch {
            userDatabase.getUsersDao().deleteUser(user)
        }
    }
}