package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.venue.VenueDetailsDataDom
import com.trifcdr.lifestylehub.domain.repositories.VenueRepository

/**
 * Created by trifcdr.
 */
class GetVenueDetailsByIdUseCase(
    private val venueRepository: VenueRepository
){

    suspend fun execute(venueId: String): DomainResource<VenueDetailsDataDom> {
        return venueRepository.getVenueDetailsById(
            id = venueId
        )
    }
}
