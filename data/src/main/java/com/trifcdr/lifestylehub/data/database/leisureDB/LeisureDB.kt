package com.trifcdr.lifestylehub.data.database.leisureDB

/**
 * Created by trifcdr.
 */
interface LeisureDB {

    suspend fun getAllLeisureItems(): MutableList<LeisureModel>

    suspend fun getLeisureById(id: Long): LeisureModel?

    suspend fun insertLeisure(leisureModel: LeisureModel)

    suspend fun deleteLeisureById(id: Long)
}