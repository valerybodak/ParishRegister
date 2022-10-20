package com.parish.register.ui.home

import androidx.lifecycle.*
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private var syncParishRegisterData = MutableLiveData<Boolean>()

    val parishRegisterLiveData = Transformations.switchMap(syncParishRegisterData) { sync ->
        parishRepository.getQuiz().asLiveData()
    }

    init {
        syncParishRegisterData.value = true
    }
}