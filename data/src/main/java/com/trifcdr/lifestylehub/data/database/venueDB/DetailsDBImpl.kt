package com.trifcdr.lifestylehub.data.database.venueDB

import com.trifcdr.lifestylehub.data.database.venueDB.room.VenueDetailsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsDBImpl(
    private val database: VenueDetailsDatabase
) : DetailsDB {

    override suspend fun getVenueDetailsById(id: String): VenueDetailsModel? {
        var details: VenueDetailsModel? = null
        CoroutineScope(Dispatchers.IO).launch {
            details = database.getVenueDetailsDao().getDetailById(id)
        }.join()
        return details
    }

    override suspend fun insertVenueDetails(details: VenueDetailsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getVenueDetailsDao().insertDetails(details)
        }
    }

    override suspend fun deleteDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            database.getVenueDetailsDao().deleteTemp()
        }
    }

}