package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.domain.repositories.LeisureRepository

/**
 * Created by trifcdr.
 */
class InsertLeisureUseCase(
    private val leisureRepository: LeisureRepository
) {

    suspend fun execute(leisure: Leisure){
        leisureRepository.insertLeisure(leisure)
    }
}