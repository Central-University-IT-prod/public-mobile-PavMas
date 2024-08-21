package com.trifcdr.lifestylehub.authorization.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by trifcdr.
 */
@Entity(tableName = "users_table")
data class AuthUser(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val login: String,
    @ColumnInfo val password: String,
    @ColumnInfo val gender: String,
    @ColumnInfo val firstName: String,
    @ColumnInfo val lastName: String,
    @ColumnInfo val email: String,
    @ColumnInfo val phone: String
)

