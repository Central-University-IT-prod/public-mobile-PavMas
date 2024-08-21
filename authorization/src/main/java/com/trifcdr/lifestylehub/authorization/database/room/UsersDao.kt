package com.trifcdr.lifestylehub.authorization.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trifcdr.lifestylehub.authorization.database.model.AuthUser

/**
 * Created by trifcdr.
 */

@Dao
interface UsersDao {


    @Query("select * from users_table where login=:login")
    fun getUserByLogin(login: String): AuthUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: AuthUser)

    @Delete
    fun deleteUser(user: AuthUser)

    @Query("DELETE FROM users_table")
    fun deleteAllDetails()
}