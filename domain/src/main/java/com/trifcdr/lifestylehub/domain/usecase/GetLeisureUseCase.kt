package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.domain.repositories.LeisureRepository

/**
 * Created by trifcdr.
 */
class GetLeisureUseCase(
    private val leisureRepository: LeisureRepository
) {

    suspend fun execute(): DomainResource<List<Leisure>>{
        return leisureRepository.getLeisureItems()
    }
}