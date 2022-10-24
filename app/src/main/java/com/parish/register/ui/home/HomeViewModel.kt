package com.parish.register.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.common.Resource
import com.parish.register.model.ListItem
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val parishRepository: ParishRegisterRepository
) : ViewModel() {

    private var mergedListFlow: Flow<Resource<List<ListItem>>> = merge(
        parishRepository.getBornList(),
        parishRepository.getMarriageList()
    )
    var parishRegisterLiveData = MutableLiveData<Resource<List<ListItem>>>()

    fun getLists() {
        viewModelScope.launch {
            mergedListFlow.collect { resource ->
                parishRegisterLiveData.value?.data?.let {
                    resource.data =
                        it.plus(resource.data ?: emptyList())
                }
                parishRegisterLiveData.postValue(resource)
            }
        }

    }
}