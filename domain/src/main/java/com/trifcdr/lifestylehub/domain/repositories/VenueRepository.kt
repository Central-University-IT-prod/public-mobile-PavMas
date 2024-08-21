package com.trifcdr.lifestylehub.domain.repositories

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.domain.models.venue.VenueDetailsDataDom
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.VenueData

/**
 * Created by trifcdr.
 */
interface VenueRepository {


    suspend fun getVenueDetailsById(id: String): DomainResource<VenueDetailsDataDom>

    suspend fun getVenueByLocation(location: UserLocation, radius: Int, limit: Int, offset: Int): DomainResource<VenueData>

    suspend fun deleteDetails()

}