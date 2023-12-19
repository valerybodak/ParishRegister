package com.parish.register.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parish.register.common.SharedPrefsManager
import com.parish.register.model.FilterType
import com.parish.register.model.ListFilter
import com.parish.register.model.SortingType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    private val _filterLiveData = MutableLiveData<ListFilter>()
    val filterLiveData: LiveData<ListFilter> = _filterLiveData

    fun getFilter() {
        _filterLiveData.value = sharedPrefsManager.getListFilter()
    }

    fun saveFilter(filterType: FilterType, periodFrom: Int, periodTo: Int, sortingType: SortingType) {
        sharedPrefsManager.saveListFilter(
            ListFilter(
                type = filterType,
                periodFrom = periodFrom,
                periodTo = periodTo,
                sortingType = sortingType
            )
        )
    }
}