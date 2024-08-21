package com.trifcdr.lifestylehub.data.database.leisureDB.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trifcdr.lifestylehub.data.database.leisureDB.LeisureModel

/**
 * Created by trifcdr.
 */

@Dao
interface LeisureDao {

    @Query("SELECT * from leisure_table")
    fun getAllLeisureItems(): MutableList<LeisureModel>

    @Query("select * from leisure_table where id=:id")
    fun getLeisureById(id: Long): LeisureModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeisure(leisure: LeisureModel)

    @Query("DELETE FROM leisure_table where id=:id")
    suspend fun deleteLeisureById(id: Long)
}