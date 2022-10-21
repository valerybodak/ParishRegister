package com.parish.register.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.parish.register.common.Resource
import com.parish.register.model.ListItem
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private var syncParishRegisterData = MutableLiveData<Boolean>()

    //val parishRegisterLiveData = MediatorLiveData

    /*val parishRegisterLiveData = Transformations.switchMap(syncParishRegisterData) { sync ->
        parishRepository.getBornList().asLiveData()
    }*/

    fun getLists() {
        viewModelScope.launch {
            val mergedFlows: Flow<Resource<List<ListItem>>> =
                merge(parishRepository.getBornList(), parishRepository.getMarriageList())
            // Trigger the flow and consume its elements using collect
            mergedFlows.collect { resource ->
                Log.e("LOG-1: ", resource.data.toString())
                // Update View with the latest favorite news
                //syncParishRegisterData.postValue(true)
            }
        }

    }
}