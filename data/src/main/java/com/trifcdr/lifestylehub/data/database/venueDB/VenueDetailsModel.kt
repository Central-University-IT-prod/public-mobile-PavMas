package com.trifcdr.lifestylehub.data.database.venueDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by trifcdr.
 */

@Entity(tableName = "venueDetails_table")
data class VenueDetailsModel(
   @PrimaryKey(autoGenerate = false) val id: String,
   @ColumnInfo val name: String,
   @ColumnInfo val address: String,
   @ColumnInfo val url: String,
   @ColumnInfo val bestPhoto: String,
   @ColumnInfo val categories: List<String>,
   @ColumnInfo val phone: String,
   @ColumnInfo val hoursStatus: String,
   @ColumnInfo val photos: List<String>,
   @ColumnInfo val reasons: List<String>,
   @ColumnInfo val temp: Boolean
)