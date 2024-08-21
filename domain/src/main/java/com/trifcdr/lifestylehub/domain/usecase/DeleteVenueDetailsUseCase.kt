package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.repositories.VenueRepository

/**
 * Created by trifcdr.
 */
class DeleteVenueDetailsUseCase(
    private val venueRepository: VenueRepository
){

    suspend fun execute(){
        venueRepository.deleteDetails()
    }
}