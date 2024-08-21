package com.trifcdr.lifestylehub.authorization.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trifcdr.lifestylehub.authorization.database.model.AuthUser

/**
 * Created by trifcdr.
 */

@Database(
    entities = [AuthUser::class],
    version = 1
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao

    companion object{
        private var downloadDatabase: UsersDatabase? = null

        fun getInstance(context: Context) : UsersDatabase {
            if(downloadDatabase == null){
                downloadDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java,
                    "users_db")
                    .build()
            }
            return downloadDatabase!!
        }
    }

}