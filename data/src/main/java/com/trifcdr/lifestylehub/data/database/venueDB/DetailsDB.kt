package com.trifcdr.lifestylehub.data.database.venueDB

/**
 * Created by trifcdr.
 */
interface DetailsDB {


    suspend fun getVenueDetailsById(id: String): VenueDetailsModel?

    suspend fun insertVenueDetails(details: VenueDetailsModel)

    suspend fun deleteDetails()

}