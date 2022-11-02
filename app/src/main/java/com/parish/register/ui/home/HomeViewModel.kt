package com.parish.register.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.common.Resource
import com.parish.register.model.*
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private val resultList = mutableListOf<ListItem>()
    var parishRegisterLiveData = MutableLiveData<List<ListItem>>()

    fun getLists(filter: ListFilter) {
        viewModelScope.launch {
            merge(
                parishRepository.getBornList(),
                parishRepository.getMarriageList(),
                parishRepository.getDiedList()
            ).collect { resource ->
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