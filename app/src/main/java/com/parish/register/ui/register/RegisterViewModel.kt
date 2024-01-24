package com.parish.register.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
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
class RegisterViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private var combinedList = mutableListOf<RegisterItem>()
    private val dateFormat = SimpleDateFormat(CommonConsts.BACKEND_DATE_FORMAT, Locale.ENGLISH)

    private val _parishRegisterLiveData = MutableLiveData<Resource<out List<RegisterItem>>>()
    val parishRegisterLiveData: LiveData<Resource<out List<RegisterItem>>> = _parishRegisterLiveData

    fun getLists(forceSync: Boolean = false) {
        combinedList.clear()
        viewModelScope.launch {
            merge(
                parishRepository.getBornListFlow(forceSync),
                parishRepository.getMarriageListFlow(forceSync),
                parishRepository.getDiedListFlow(forceSync)
            ).collect { resource ->
                mergeResource(resource)
            }
        }
    }

    private fun mergeResource(resource: Resource<out List<RegisterItem>>) {
        when (resource) {
            is Resource.Error ->
                _parishRegisterLiveData.value = resource
            is Resource.Success -> {
                applyResource(resource)
            }
            is Resource.Loading -> {
                applyResource(resource)
            }
        }
    }

    private fun applyResource(resource: Resource<out List<RegisterItem>>){
        val data = resource.data ?: emptyList()
        removeExistingItems(data)
        combinedList.addAll(data)
        if (areAllListsReceived()) {
            _parishRegisterLiveData.value = Resource.Success(data = filterList())
        }
    }

    private fun areAllListsReceived(): Boolean {
        return combinedList.firstOrNull { it is Born } != null
                && combinedList.firstOrNull { it is Marriage } != null
                && combinedList.firstOrNull { it is Died } != null
    }

    private fun removeExistingItems(list: List<RegisterItem>){
        val item = list.firstOrNull()
        when (item) {
            is Born -> {
                combinedList.removeAll { it is Born }
            }
            is Marriage -> {
                combinedList.removeAll { it is Marriage }
            }
            is Died -> {
                combinedList.removeAll { it is Died }
            }
        }
    }

    private fun filterList(): List<RegisterItem> {
        val filter = sharedPrefsManager.getListFilter()
        val filteredList = combinedList.filter { listItem ->
            //check filter type
            val filterType = filter.type
            val typeMatched = if (filterType == FilterType.ALL) {
                true
            } else {
                when (listItem) {
                    is Born -> filterType == FilterType.BORN
                    is Marriage -> filterType == FilterType.MARRIAGES
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
        //sorting
        return when (filter.sortingType) {
            SortingType.BY_DATE_ASC -> filteredList.sortedBy { item -> item.getSortDate() }
            SortingType.BY_DATE_DESC -> filteredList.sortedByDescending { item -> item.getSortDate() }
            else -> filteredList.sortedBy { item -> item.getSortName() }
        }
    }
}