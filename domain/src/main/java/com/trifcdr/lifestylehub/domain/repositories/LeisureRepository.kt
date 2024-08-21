package com.trifcdr.lifestylehub.domain.repositories

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure

/**
 * Created by trifcdr.
 */
interface LeisureRepository {

    suspend fun getLeisureItems(): DomainResource<List<Leisure>>

    suspend fun getLeisureById(id: Long): DomainResource<Leisure>

    suspend fun insertLeisure(leisure: Leisure)

    suspend fun deleteLeisureById(id: Long)

}