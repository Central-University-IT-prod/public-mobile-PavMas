package com.trifcdr.lifestylehub.data.network.api.venue

import com.example.lifestylehub.data.Resource
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends.VenueResponse
import com.trifcdr.lifestylehub.data.network.models.venueModels.auth.AuthResponse
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails.VenueDetailsResponse
import com.trifcdr.lifestylehub.domain.models.location.UserLocation

/**
 * Created by trifcdr.
 */
interface FSVenueApi {

    suspend fun auth(): Resource<AuthResponse>

    suspend fun getVenueRecommendationsByLocation(oauthToken: String, location: UserLocation, radius: Int, limit: Int, offset: Int): Resource<VenueResponse>

    suspend fun getVenueDetails(oauthToken: String, venueId: String): Resource<VenueDetailsResponse>

}