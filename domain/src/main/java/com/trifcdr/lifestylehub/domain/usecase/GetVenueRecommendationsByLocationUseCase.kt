package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.VenueData
import com.trifcdr.lifestylehub.domain.repositories.VenueRepository

/**
 * Created by trifcdr.
 */
class GetVenueRecommendationsByLocationUseCase(
    private val venueRepository: VenueRepository
) {

    suspend fun execute(location: UserLocation, radius: Int, limit: Int, offset: Int): DomainResource<VenueData>{
        return venueRepository.getVenueByLocation(
            location = location,
            radius = radius,
            limit = limit,
            offset = offset
        )
    }
}