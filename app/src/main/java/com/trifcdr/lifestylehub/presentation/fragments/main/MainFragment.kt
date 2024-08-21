package com.trifcdr.lifestylehub.presentation.fragments.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.databinding.FragmentMainBinding
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.presentation.recycler.BaseRecyclerAdapter
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.VenueItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomScrollListener
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt


/**
 * Created by trifcdr.
 */

@AndroidEntryPoint
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    @Inject lateinit var viewHoldersManager: ViewHoldersManager


    private val vm: MainViewModel by viewModels()
    private lateinit var recycler: RecyclerView
    private val items = mutableListOf<StringId>()

    private lateinit var layoutManager1: LinearLayoutManager

    private lateinit var location: UserLocation

    private var totalPages: Int = 0
    private var showedPages: Int = 0
    private var offset: Int = 1

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    private var isFirstStart = true

    private lateinit var itemsAdapter: BaseRecyclerAdapter



    companion object {
            var permission = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)


    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(isFirstStart){
            val requestMultiplePermissions = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                val granted = permissions.entries.all {
                    it.value
                }
                if (granted){
                    vm.startTrackingLocation(2000L, 500F)

                }

            }
            requestMultiplePermissions.launch(permission)
            binding.weatherWidget.setShimmer()
            binding.progressCircular.visibility = View.VISIBLE
            isFirstStart = false
        }
        recycler = binding.recyclerVenue
        vm.resultLiveLocation.observe(viewLifecycleOwner) {
            location = it
            binding.progressCircular.visibility = View.VISIBLE
            itemsAdapter.submitList(null)
            items.clear()
            vm.getVenueRecommendsByLocation(
                userLocation = location,
                radius = 5000,
                limit = 10,
                offset = 0
            )
            vm.getWeatherByLocation(
                location
            )
        }

        itemsAdapter = BaseRecyclerAdapter(viewHoldersManager, CustomItemClickListener { itemId, _, _ ->
            val action = MainFragmentDirections.actionMainFragmentToVenueFragment(itemId)
            Navigation.findNavController(binding.root).navigate(action)
        })

        itemsAdapter.submitList(items)

        layoutManager1 = LinearLayoutManager(requireContext())

        recycler.apply {
            layoutManager = layoutManager1
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = itemsAdapter
            setScrollListener()
        }

        vm.resultLiveWeather.observe(viewLifecycleOwner) { weatherData ->
            if (weatherData is DomainResource.Success) {
                val weather = weatherData.result
                binding.weatherWidget.apply {
                    removeShimmer(true)
                    setCity(weather.name)
                    setWeatherStatus(weather.weather[0].description)
                    setFeelLike("${weather.main.feelsLike.roundToInt()}")
                    setTemperature("${weather.main.temp.roundToInt()}")
                    setWeatherIcon(weather.weather[0].icon)
                    setTempSpread(
                        "${weather.main.tempMin.roundToInt()}",
                        "${weather.main.tempMax.roundToInt()}"
                    )
                }
            }
            if (weatherData is DomainResource.Failure) {
                binding.weatherWidget.removeShimmer(false)
            }


        }
        vm.resultVenueRecommendsLiveData.observe(viewLifecycleOwner){
                venueData ->
                if (venueData is DomainResource.Success) {
                    isLoading = false
                    totalPages = venueData.result.totalResults
                    venueData.result.results?.forEach{
                        items.add(VenueItem(
                            venueId = it.venue?.id ?: "",
                            image = if (it.photo!=null) "${it.photo!!.prefix}original${it.photo!!.suffix}" else null,
                            category = it.venue?.categories?.get(0)?.shortName ?: "",
                            name = it.venue?.name ?: "",
                            address = it.venue?.location?.address ?: getString(R.string.addr)
                        ))
                    }
                    itemsAdapter.submitList(items)
                    binding.progressCircular.visibility = View.GONE

                }
                if (venueData is DomainResource.Empty){
                    isLoading = false
                    totalPages = 0
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(context, getString(R.string.noPlaces), Toast.LENGTH_SHORT).show()
                }
        }

        vm.errorMessageLiveData.observe(viewLifecycleOwner) {
            if (it == "LocationError") {
                binding.weatherWidget.removeShimmer(false)
                binding.errorVenue.text = getString(R.string.places)
                binding.errorVenue.visibility = View.VISIBLE
                binding.progressCircular.visibility = View.GONE
            }
        }
    }

    private fun setScrollListener(){
        recycler.addOnScrollListener(object : CustomScrollListener(layoutManager1, totalPages){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                if(showedPages <= totalPages){
                    binding.progressCircular.visibility = View.VISIBLE
                    isLoading = true
                    offset += 10
                    showedPages += 10
                    vm.getVenueRecommendsByLocation(
                        userLocation = location,
                        radius = 5000,
                        limit = 10,
                        offset = offset
                    )
                }
                else{
                    isLastPage = true
                }

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.stopTrackingLocation()
    }



}