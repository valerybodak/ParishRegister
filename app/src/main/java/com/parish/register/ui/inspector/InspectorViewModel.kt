package com.parish.register.ui.inspector

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parish.register.repository.ParishRegisterRepository
import com.parish.register.utils.StringComparator
import com.parish.register.utils.toLowerCaseLocalized
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
            duplicates.addAll(getMarriageDuplicates())
            duplicates.addAll(getDiedDuplicates())
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
                        && item1.page == item2.page
                        && item1.birthDate == item2.birthDate
                        && item1.gender == item2.gender
                    ) {
                        duplicates.add(
                            DuplicateItem(
                                similarity = calculateSimilarity(item1.fullName, item2.fullName),
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

    private suspend fun getMarriageDuplicates(): List<DuplicateItem> {
        val duplicates = mutableListOf<DuplicateItem>()
        val marriage1 = registerRepository.getMarriageList()
        val marriage2 = marriage1.toList()
        marriage1.forEachIndexed { index1, item1 ->
            marriage2.forEachIndexed { index2, item2 ->
                if (index1 != index2) {
                    if (item1.fundNumber == item2.fundNumber
                        && item1.inventoryNumber == item2.inventoryNumber
                        && item1.caseNumber == item2.caseNumber
                        && item1.page == item2.page
                        && item1.date == item2.date
                    ) {
                        duplicates.add(
                            DuplicateItem(
                                similarity = calculateSimilarity(item1.groom, item2.groom),
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

    private suspend fun getDiedDuplicates(): List<DuplicateItem> {
        val duplicates = mutableListOf<DuplicateItem>()
        val died1 = registerRepository.getDiedList()
        val died2 = died1.toList()
        died1.forEachIndexed { index1, item1 ->
            died2.forEachIndexed { index2, item2 ->
                if (index1 != index2) {
                    if (item1.fundNumber == item2.fundNumber
                        && item1.inventoryNumber == item2.inventoryNumber
                        && item1.caseNumber == item2.caseNumber
                        && item1.page == item2.page
                        && item1.deathDate == item2.deathDate
                    ) {
                        duplicates.add(
                            DuplicateItem(
                                similarity = calculateSimilarity(item1.fullName, item2.fullName),
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

    private fun calculateSimilarity(str1: String, str2: String): Double{
        return StringComparator.similarity(removeCommonItems(str1), removeCommonItems(str2))
    }

    private fun removeCommonItems(str: String): String{
        return str.toLowerCaseLocalized()
            .replace(" ", "")
            .replace("крестьянин", "")
            .replace("крестьянка", "")
            .replace("д.", "")
            .replace(",", "")
            .replace("1-й", "")
            .replace("2-й", "")
            .replace("брак", "")
            .replace("лет", "")
            .replace("года", "")
            .replace("православного", "")
            .replace("исповедания", "")
            .replace("прихожанин", "")
            .replace("церкви", "")
    }
}