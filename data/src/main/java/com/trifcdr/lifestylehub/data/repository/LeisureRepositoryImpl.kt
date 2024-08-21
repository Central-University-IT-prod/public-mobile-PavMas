package com.trifcdr.lifestylehub.data.repository

import com.trifcdr.lifestylehub.data.database.leisureDB.LeisureDB
import com.trifcdr.lifestylehub.data.mappers.mapToDBModel
import com.trifcdr.lifestylehub.data.mappers.mapToDomainLeisure
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.domain.repositories.LeisureRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeisureRepositoryImpl(
    private val database: LeisureDB
) : LeisureRepository {

    override suspend fun getLeisureItems(): DomainResource<List<Leisure>> {
        var result: DomainResource<List<Leisure>> = DomainResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            val list = database.getAllLeisureItems()
            if (list.isNotEmpty()){
                result = DomainResource.Success(
                    list.map {
                        mapToDomainLeisure(it)
                    }
                )
            }
        }.join()
        return result
    }

    override suspend fun getLeisureById(id: Long): DomainResource<Leisure> {
        var result: DomainResource<Leisure> = DomainResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            val leisure = database.getLeisureById(id)
            if (leisure != null) result = DomainResource.Success(mapToDomainLeisure(leisure))
        }.join()
        return result
    }

    override suspend fun insertLeisure(leisure: Leisure) {
        CoroutineScope(Dispatchers.IO).launch {
            database.insertLeisure(mapToDBModel(leisure))
        }
    }

    override suspend fun deleteLeisureById(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteLeisureById(id)
        }
    }
}