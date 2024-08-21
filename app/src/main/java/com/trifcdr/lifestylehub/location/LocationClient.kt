package com.trifcdr.lifestylehub.location

import android.location.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by trifcdr.
 */

interface LocationClient {

    fun getLocationUpdates(interval: Long, distance: Float) : Flow<Location>

    class LocationException(message: String): Exception()
}