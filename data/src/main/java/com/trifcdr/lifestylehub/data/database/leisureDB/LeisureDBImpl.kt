package com.trifcdr.lifestylehub.data.database.leisureDB

import com.trifcdr.lifestylehub.data.database.leisureDB.room.LeisureDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeisureDBImpl(
    private val database: LeisureDatabase
) : LeisureDB {

    override suspend fun getAllLeisureItems(): MutableList<LeisureModel> {
        var list = mutableListOf<LeisureModel>()
        CoroutineScope(Dispatchers.IO).launch {
            list = database.getLeisureDao().getAllLeisureItems()
        }.join()
        return list
    }

    override suspend fun getLeisureById(id: Long): LeisureModel? {
        var leisure: LeisureModel? = null
        CoroutineScope(Dispatchers.IO).launch {
            leisure = database.getLeisureDao().getLeisureById(id)
        }.join()
        return leisure
    }

    override suspend fun insertLeisure(leisureModel: LeisureModel) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getLeisureDao().insertLeisure(leisureModel)
        }
    }

    override suspend fun deleteLeisureById(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getLeisureDao().deleteLeisureById(id)
        }
    }
}