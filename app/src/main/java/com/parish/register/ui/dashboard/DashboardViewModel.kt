package com.parish.register.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val registerRepository: ParishRegisterRepository
) : ViewModel() {

    val dashboardLiveData = MutableLiveData<DashboardUiState>()

    fun getDashboard() {
        viewModelScope.launch {
            val bornCount = registerRepository.getBornCount()
            val marriageCount = registerRepository.getMarriageCount()
            val diedCount = registerRepository.getDiedCount()

            dashboardLiveData.value = DashboardUiState(
                bornCount = bornCount,
                marriageCount = marriageCount,
                diedCount = diedCount
            )
        }
    }
}