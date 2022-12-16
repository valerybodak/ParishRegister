package com.parish.register.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.common.SharedPrefsManager
import com.parish.register.db.dao.DaoBorn
import com.parish.register.db.dao.DaoDied
import com.parish.register.db.dao.DaoMarriage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val bornDao: DaoBorn,
    private val marriageDao: DaoMarriage,
    private val diedDao: DaoDied
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