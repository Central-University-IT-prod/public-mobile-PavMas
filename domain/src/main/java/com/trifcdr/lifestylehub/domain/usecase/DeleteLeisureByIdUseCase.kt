package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.repositories.LeisureRepository

/**
 * Created by trifcdr.
 */
class DeleteLeisureByIdUseCase(
    private val repository: LeisureRepository
) {

    suspend fun execute(id: Long){
        repository.deleteLeisureById(id)
    }

}