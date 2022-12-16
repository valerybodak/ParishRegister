package com.parish.register.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.db.dao.BornDao
import com.parish.register.db.dao.DiedDao
import com.parish.register.db.dao.MarriageDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val bornDao: BornDao,
    private val marriageDao: MarriageDao,
    private val diedDao: DiedDao
) : ViewModel() {

    val dashboardLiveData = MutableLiveData<DashboardUiState>()

    fun getDashboard() {
        viewModelScope.launch {
            val bornCount = bornDao.getBornCount()
            val marriageCount = marriageDao.getMarriageCount()
            val diedCount = diedDao.getDiedCount()

            dashboardLiveData.value = DashboardUiState(
                bornCount = bornCount,
                marriageCount = marriageCount,
                diedCount = diedCount
            )
        }
    }
}