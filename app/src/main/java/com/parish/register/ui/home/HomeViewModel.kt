package com.parish.register.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.common.Resource
import com.parish.register.model.Born
import com.parish.register.model.Died
import com.parish.register.model.ListItem
import com.parish.register.model.Marriage
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    /*private var mergedListFlow: Flow<Resource<List<ListItem>>> = merge(
        parishRepository.getBornList(),
        parishRepository.getMarriageList(),
        parishRepository.getDiedList()
    )*/
    private val resultList = mutableListOf<ListItem>()
    var parishRegisterLiveData = MutableLiveData<List<ListItem>>()

    fun getLists() {
        viewModelScope.launch {
            merge(
                parishRepository.getBornList(),
                parishRepository.getMarriageList(),
                parishRepository.getDiedList()
            ).collect { resource ->
                if(resource is Resource.Loading) {
                    Log.e(
                        "RESOURCE-01: ",
                         "LOADING :: " + Calendar.getInstance().timeInMillis
                    )
                }else if(resource is Resource.Success){
                    Log.e(
                        "RESOURCE-01: ",
                        "SUCCESS :: " + Calendar.getInstance().timeInMillis
                    )
                }else if(resource is Resource.Error){
                    Log.e(
                        "RESOURCE-01: ",
                        "ERROR :: " + Calendar.getInstance().timeInMillis
                    )
                }
                if (resource is Resource.Success) {
                    resultList.addAll(resource.data ?: emptyList())
                    if (areAllListsReceived()) {
                        parishRegisterLiveData.postValue(resultList.sortedBy { item -> item.getSortDate() })
                    }
                }
            }
        }
    }

    private fun areAllListsReceived(): Boolean {
        return resultList.firstOrNull { it is Born } != null
                && resultList.firstOrNull { it is Marriage } != null
                && resultList.firstOrNull { it is Died } != null
    }
}