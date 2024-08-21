package com.trifcdr.lifestylehub.data.database.leisureDB.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trifcdr.lifestylehub.data.database.leisureDB.LeisureModel

/**
 * Created by trifcdr.
 */

@Database(
    entities = [LeisureModel::class],
    version = 1
)
abstract class LeisureDatabase: RoomDatabase() {

    abstract fun getLeisureDao(): LeisureDao

}