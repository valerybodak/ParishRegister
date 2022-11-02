package com.parish.register.ui.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parish.register.common.SharedPrefsManager
import com.parish.register.model.ListFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    val filterLiveData = MutableLiveData<ListFilter>()

    fun getFilter() {
        filterLiveData.value = sharedPrefsManager.getListFilter()
    }
}