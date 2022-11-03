package com.parish.register.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.common.CommonConsts
import com.parish.register.common.Resource
import com.parish.register.model.*
import com.parish.register.repository.ParishRegisterRepository
import com.parish.register.utils.parseYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private var combinedList = mutableListOf<ListItem>()
    private val dateFormat = SimpleDateFormat(CommonConsts.BACKEND_DATE_FORMAT, Locale.ENGLISH)

    var parishRegisterLiveData = MutableLiveData<List<ListItem>>()

    fun getLists(filter: ListFilter) {
        viewModelScope.launch {
            merge(
                parishRepository.getBornList(),
                parishRepository.getMarriageList(),
                parishRepository.getDiedList()
            ).collect { resource ->
                if (resource is Resource.Success) {
                    combinedList.addAll(resource.data ?: emptyList())
                    if (areAllListsReceived()) {
                        submitList(filter)
                    }
                }
            }
        }
    }

    private fun areAllListsReceived(): Boolean {
        return combinedList.firstOrNull { it is Born } != null
                && combinedList.firstOrNull { it is Marriage } != null
                && combinedList.firstOrNull { it is Died } != null
    }

    private fun submitList(filter: ListFilter) {
        var filteredList = combinedList.filter { listItem ->
            var year = 0
            try {
                dateFormat.parse(listItem.getSortDate())?.let { date ->
                    year = date.parseYear()
                }
            } catch (e: Exception) { }
            year != 0 && year >= filter.periodFrom && year <= filter.periodTo
        }
        filteredList = filteredList.sortedBy { item -> item.getSortDate() }.toMutableList()
        parishRegisterLiveData.postValue(filteredList)
    }
}