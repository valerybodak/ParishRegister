package com.parish.register.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.parish.register.repository.ParishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRepository
) : ViewModel() {

    private var syncParishRegisterData = MutableLiveData<Boolean>()

    val parishRegisterLiveData = Transformations.switchMap(syncParishRegisterData) { sync ->
        parishRepository.getQuiz(category).asLiveData()
    }

    init {
        syncParishRegisterData.value = true
    }
}