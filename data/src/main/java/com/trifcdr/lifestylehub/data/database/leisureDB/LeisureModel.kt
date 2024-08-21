package com.trifcdr.lifestylehub.data.database.leisureDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by trifcdr.
 */

@Entity(tableName = "leisure_table")
data class LeisureModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo val name: String,
    @ColumnInfo val venueId: String? = null,
    @ColumnInfo val date: String,
    @ColumnInfo val notes: String
)