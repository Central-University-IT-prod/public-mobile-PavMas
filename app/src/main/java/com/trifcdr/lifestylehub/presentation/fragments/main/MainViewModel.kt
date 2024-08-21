package com.trifcdr.lifestylehub.presentation.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.domain.models.weather.WeatherData
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.usecase.GetWeatherByLocationUseCase
import com.trifcdr.lifestylehub.location.LocationClient
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.VenueData
import com.trifcdr.lifestylehub.domain.usecase.GetVenueRecommendationsByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by trifcdr.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getVenueRecommendationsByLocationUseCase: GetVenueRecommendationsByLocationUseCase,
    private val locationClient: LocationClient
) : ViewModel() {

    private var resultLiveWeatherMutable = MutableLiveData<DomainResource<WeatherData>?>()
    val resultLiveWeather: LiveData<DomainResource<WeatherData>?>
        get() = resultLiveWeatherMutable

    private var resultLiveLocationMutable = MutableLiveData<UserLocation>()
    val resultLiveLocation: LiveData<UserLocation>
        get() = resultLiveLocationMutable

    private var errorLiveDataMutable = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorLiveDataMutable

    private var resultVenueRecommendsLiveDataMutable = MutableLiveData<DomainResource<VenueData>>()
    val resultVenueRecommendsLiveData: LiveData<DomainResource<VenueData>>
        get() = resultVenueRecommendsLiveDataMutable

    private var liveDataMutable = MutableLiveData<List<DomainResource<VenueData>>>()

    private lateinit var locationScope: CoroutineScope

    private val mutableListReccommends = mutableListOf<DomainResource<VenueData>>()

    fun getWeatherByLocation(userLocation: UserLocation) = viewModelScope.launch {
        var res: DomainResource<WeatherData> = DomainResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            res = getWeatherByLocationUseCase.execute(userLocation)
        }.join()
        resultLiveWeatherMutable.value = res
    }


    fun getVenueRecommendsByLocation(userLocation: UserLocation, radius: Int, limit: Int, offset: Int) = viewModelScope.launch {
        var res: DomainResource<VenueData> = DomainResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            res = getVenueRecommendationsByLocationUseCase.execute(
                location = userLocation,
                radius = radius,
                limit = limit,
                offset = offset
            )
        }.join()
        mutableListReccommends.add(res)
        resultVenueRecommendsLiveDataMutable.value = res
    }


    //start tracking location in custom scope
    fun startTrackingLocation(interval: Long, distance: Float) {
        locationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        locationClient.getLocationUpdates(interval, distance)
            .onEach {
                viewModelScope.launch {
                    resultLiveLocationMutable.value = UserLocation(
                        lat = it.latitude.toFloat(),
                        lon = it.longitude.toFloat()
                    )
                }
            }
            .catch {
                viewModelScope.launch { errorLiveDataMutable.value = LOCATION_ERROR }
            }
            .launchIn(locationScope)
    }

    //stop tracking location and clear livedata
    fun stopTrackingLocation(){
        errorLiveDataMutable = MutableLiveData<String>()
        resultLiveLocationMutable = MutableLiveData<UserLocation>()
        resultVenueRecommendsLiveDataMutable = MutableLiveData<DomainResource<VenueData>>()
        liveDataMutable.value = mutableListReccommends
    }




    companion object{
        private const val LOCATION_ERROR: String = "LocationError"
    }
}
