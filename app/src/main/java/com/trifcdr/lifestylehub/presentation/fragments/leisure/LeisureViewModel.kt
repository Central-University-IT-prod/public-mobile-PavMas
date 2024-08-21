package com.trifcdr.lifestylehub.presentation.fragments.leisure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.domain.usecase.DeleteLeisureByIdUseCase
import com.trifcdr.lifestylehub.domain.usecase.GetLeisureUseCase
import com.trifcdr.lifestylehub.domain.usecase.InsertLeisureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by trifcdr.
 */

@HiltViewModel
class LeisureViewModel @Inject constructor(
    private val getLeisureUseCase: GetLeisureUseCase,
    private val insertLeisureUseCase: InsertLeisureUseCase,
    private val deleteLeisureByIdUseCase: DeleteLeisureByIdUseCase
) : ViewModel() {

    private lateinit var invoker: () -> Unit

    fun invoke(){
        invoker.invoke()
    }

    fun getLeisureList() = callbackFlow {
        invoker = {
            viewModelScope.launch {
                try {
                    trySend(getLeisureUseCase.execute())
                } catch (e: Exception) {
                    trySend(DomainResource.Failure(e))
                }
            }
        }
        awaitClose{}
    }


    fun insertLeisure(leisure: Leisure) = viewModelScope.launch{
        insertLeisureUseCase.execute(leisure)
        delay(500)
        invoke()
    }

    fun deleteLeisure(id: Long) = viewModelScope.launch {
        deleteLeisureByIdUseCase.execute(id)
        delay(500)
        invoke()
    }


}