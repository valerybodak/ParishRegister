package com.parish.register.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parish.register.common.SharedPrefsManager
import com.parish.register.model.FilterType
import com.parish.register.model.ListFilter
import com.parish.register.model.SortingType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    val filterLiveData = MutableLiveData<ListFilter>()

    fun getFilter() {
        filterLiveData.value = sharedPrefsManager.getListFilter()
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