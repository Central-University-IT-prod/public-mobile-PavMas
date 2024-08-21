package com.trifcdr.lifestylehub.data.database.venueDB.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trifcdr.lifestylehub.data.database.venueDB.VenueDetailsModel

/**
 * Created by trifcdr.
 */


@Database(
    entities = [VenueDetailsModel::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class VenueDetailsDatabase: RoomDatabase() {

    abstract fun getVenueDetailsDao(): VenueDetailsDao


}