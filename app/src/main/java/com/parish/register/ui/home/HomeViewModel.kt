package com.parish.register.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.common.CommonConsts
import com.parish.register.common.Resource
import com.parish.register.common.SharedPrefsManager
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
    private val sharedPrefsManager: SharedPrefsManager,
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private var combinedList = mutableListOf<ListItem>()
    private val dateFormat = SimpleDateFormat(CommonConsts.BACKEND_DATE_FORMAT, Locale.ENGLISH)

    var parishRegisterLiveData = MutableLiveData<List<ListItem>>()

    fun getLists() {
        combinedList.clear()
        viewModelScope.launch {
            merge(
                parishRepository.getBornList(),
                parishRepository.getMarriageList(),
                parishRepository.getDiedList()
            ).collect { resource ->
                if (resource is Resource.Success) {
                    combinedList.addAll(resource.data ?: emptyList())
                    if (areAllListsReceived()) {
                        submitList(sharedPrefsManager.getListFilter())
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
            //check filter type
            val filterType = filter.type
            val typeMatched = if (filterType == FilterType.NO_FILTERS) {
                true
            } else {
                when (listItem) {
                    is Born -> filterType == FilterType.BORN
                    is Marriage -> filterType == FilterType.MARRIAGE
                    is Died -> filterType == FilterType.DIED
                    else -> true
                }
            }
            //check period
            var year = 0
            try {
                dateFormat.parse(listItem.getSortDate())?.let { date ->
                    year = date.parseYear()
                }
            } catch (e: Exception) {
            }
            val periodMatched = year != 0 && year >= filter.periodFrom && year <= filter.periodTo
            typeMatched && periodMatched
        }
        filteredList = filteredList.sortedBy { item -> item.getSortDate() }
        parishRegisterLiveData.postValue(filteredList)
    }
}