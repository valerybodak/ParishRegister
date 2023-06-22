package com.parish.register.ui.inspector

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.repository.ParishRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectorViewModel @Inject constructor(
    private val registerRepository: ParishRegisterRepository
) : ViewModel() {

    val duplicatesLiveData = MutableLiveData<List<DuplicateItem>>()

    fun getDuplicates() {
        viewModelScope.launch {
            val duplicates = mutableListOf<DuplicateItem>()
            duplicates.addAll(getBornDuplicates())
            duplicatesLiveData.value = duplicates
        }
    }

    private suspend fun getBornDuplicates(): List<DuplicateItem> {
        val duplicates = mutableListOf<DuplicateItem>()
        val born1 = registerRepository.getBornList()
        val born2 = born1.toList()
        born1.forEachIndexed { index1, item1 ->
            born2.forEachIndexed { index2, item2 ->
                if (index1 != index2) {
                    if (item1.fundNumber == item2.fundNumber
                        && item1.inventoryNumber == item2.inventoryNumber
                        && item1.caseNumber == item2.caseNumber
                        && item1.birthDate == item2.birthDate
                        && item1.gender == item2.gender
                    ) {
                        duplicates.add(
                            DuplicateItem(
                                item1 = item1,
                                item2 = item2
                            )
                        )
                    }
                }
            }
        }
        return duplicates
    }
}