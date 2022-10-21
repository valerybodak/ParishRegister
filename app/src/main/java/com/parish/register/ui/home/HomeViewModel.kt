package com.parish.register.ui.home

import androidx.lifecycle.*
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private var syncParishRegisterData = MutableLiveData<Boolean>()

    //val parishRegisterLiveData = MediatorLiveData

    val parishRegisterLiveData = Transformations.switchMap(syncParishRegisterData) { sync ->
        parishRepository.getBornList().asLiveData()
    }

    init {
        syncParishRegisterData.value = true
    }

    fun getLists(){
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            parishRepository.getBornList().collect { bornList ->
                                // Update View with the latest favorite news
                syncParishRegisterData.postValue(true)
            }
        }

    }
}