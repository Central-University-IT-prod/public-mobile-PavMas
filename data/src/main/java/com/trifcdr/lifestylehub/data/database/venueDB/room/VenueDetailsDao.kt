package com.trifcdr.lifestylehub.data.database.venueDB.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trifcdr.lifestylehub.data.database.venueDB.VenueDetailsModel

/**
 * Created by trifcdr.
 */

@Dao
interface VenueDetailsDao {

    @Query("SELECT * from venueDetails_table")
    fun getAllDetails(): List<VenueDetailsModel>

    @Query("select * from venueDetails_table where id=:id")
    fun getDetailById(id: String): VenueDetailsModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(detail: VenueDetailsModel)


    @Query("DELETE FROM venueDetails_table where `temp`=:isTemp")
    suspend fun deleteTemp(isTemp: Boolean = true)
}