package com.trifcdr.lifestylehub.authorization

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trifcdr.lifestylehub.authorization.database.UsersDBImpl
import com.trifcdr.lifestylehub.authorization.database.model.AuthUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.spec.KeySpec
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private val Context.dataStore by preferencesDataStore("auth_user_preferences")

class AuthorizationApiImpl(
    context: Context,
) : AuthorizationApi {

    private val dataStore = context.dataStore

    private val userDatabase = UsersDBImpl(context)


    override suspend fun authorized(login: String, password: String): AuthResource<AuthUser> {
        var result: AuthResource<AuthUser> = AuthResource.NotAuthorized
        CoroutineScope(Dispatchers.IO).launch {
            val user = userDatabase.getUserByLogin(login)
            if (user == null){
                result = AuthResource.UserDoesNotExist
                return@launch
            }
            val spec: KeySpec =
                PBEKeySpec(
                    password.toCharArray(),
                    SALT.toByteArray(StandardCharsets.UTF_8),
                    65536,
                    128
                )
            val factory = SecretKeyFactory.getInstance(ALGORITHM)
            val hash = factory.generateSecret(spec).encoded
            val enc: Base64.Encoder = Base64.getEncoder()
            if (user.password == enc.encodeToString(hash)) {
                val dataStoreLoginKey = stringPreferencesKey("login")
                val dataStorePasswordKey = stringPreferencesKey("password")
                dataStore.edit { settings ->
                    settings[dataStoreLoginKey] = login
                    settings[dataStorePasswordKey] = enc.encodeToString(hash)
                }
                result = AuthResource.Success(user)
            } else {
                result = AuthResource.Forbidden
            }
        }.join()
        return result
    }

    override suspend fun register(user: AuthUser): AuthResource<AuthUser> {
        var result:AuthResource<AuthUser> = AuthResource.UserAlreadyRegister
        CoroutineScope(Dispatchers.IO).launch {
            val resUserDB = userDatabase.getUserByLogin(user.login)
            if (resUserDB != null) {
                return@launch
            }
            val spec: KeySpec =
                PBEKeySpec(user.password.toCharArray(), SALT.toByteArray(), 65536, 128)
            val factory = SecretKeyFactory.getInstance(ALGORITHM)
            val hash = factory.generateSecret(spec).encoded
            val enc = Base64.getEncoder()
            val newUser = AuthUser(
                id = user.id,
                login = user.login,
                password = enc.encodeToString(hash),
                gender = user.gender,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                phone = user.phone
            )
            userDatabase.insertUser(newUser)
            val dataStoreLoginKey = stringPreferencesKey("login")
            val dataStorePasswordKey = stringPreferencesKey("password")
            dataStore.edit { settings ->
                settings[dataStoreLoginKey] = newUser.login
                settings[dataStorePasswordKey] = newUser.password
            }
            result = AuthResource.Success(newUser)
        }.join()
        return result
    }

    override suspend fun getUser(): AuthResource<AuthUser> {
        var result:AuthResource<AuthUser> = AuthResource.NotAuthorized
        CoroutineScope(Dispatchers.IO).launch {
            val dataStoreLoginKey = stringPreferencesKey("login")
            val dataStorePasswordKey = stringPreferencesKey("password")
            val prefs = dataStore.data.first()
            val login = prefs[dataStoreLoginKey] ?: return@launch
            val user = userDatabase.getUserByLogin(login)
            if (user == null){
                result = AuthResource.UserDoesNotExist
                return@launch
            }
            result = if (user.password == prefs[dataStorePasswordKey]) AuthResource.Success(user)
            else AuthResource.Failure(Exception("AuthError"))
        }.join()
        return result
    }

    override suspend fun logOut() {
        CoroutineScope(Dispatchers.IO).launch {
            val dataStoreLoginKey = stringPreferencesKey("login")
            val dataStorePasswordKey = stringPreferencesKey("password")
            dataStore.edit {
                it.remove(dataStoreLoginKey)
                it.remove(dataStorePasswordKey)
            }
        }
    }

    companion object{
        const val SALT = "V000SGJ2TnU5ZjRLeXNWaDR0YkkxZz09"
        const val ALGORITHM = "PBKDF2WithHmacSHA1"

    }
}