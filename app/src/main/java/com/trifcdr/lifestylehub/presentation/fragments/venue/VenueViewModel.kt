package com.trifcdr.lifestylehub.presentation.fragments.venue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.domain.usecase.DeleteVenueDetailsUseCase
import com.trifcdr.lifestylehub.domain.usecase.GetVenueDetailsByIdUseCase
import com.trifcdr.lifestylehub.domain.usecase.InsertLeisureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by trifcdr.
 */

@HiltViewModel
class VenueViewModel @Inject constructor(
    private val getVenueDetailsByIdUseCase: GetVenueDetailsByIdUseCase,
    private val insertLeisureUseCase: InsertLeisureUseCase,
    private val deleteVenueDetailsUseCase: DeleteVenueDetailsUseCase
): ViewModel() {


    fun getVenueDetailsById(venueId: String) = flow {
        try{
            emit(getVenueDetailsByIdUseCase.execute(venueId))
        }
        catch (e: Exception){
            emit(DomainResource.Failure(e))
        }
    }

    fun insertLeisure(leisure: Leisure) = viewModelScope.launch {
        insertLeisureUseCase.execute(leisure)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            deleteVenueDetailsUseCase.execute()
        }
    }
}