package com.parish.register.repository

import com.parish.register.common.*
import com.parish.register.db.dao.DaoBorn
import com.parish.register.model.Category
import com.parish.register.model.ListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParishRegisterRepository @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val daoBorn: DaoBorn
) {

    fun getBornList(category: Category = Category.ALL): Flow<Resource<List<ListItem>>> {
        return FirebaseHelper.loadFileData(
            BORN_LIST_FILE_NAME,
            query = { daoBorn.getAllBorn().map { it.toBorn() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_PARISH_REGISTER)) },
            observeProgress = true,
            saveFetchResponse = { rawItems -> saveRegister(rawItems) }
        )
    }

    private suspend fun saveRegister(rawItems: List<String>) {
        rawItems.forEach { line ->
            line.toBornEntity()?.let { remoteBorn ->
                val localBorn = daoBorn.getBorn(remoteBorn.id)
                if (localBorn != null) {
                    daoBorn.update(remoteBorn.copy(localId = localBorn.localId))
                } else {
                    daoBorn.insert(remoteBorn)
                }
            }
        }
        sharedPrefsManager.saveLastSynced(TAG_PARISH_REGISTER)
    }

    companion object {
        private const val BORN_LIST_FILE_NAME = "born.tsv"
        private const val MARRIAGE_LIST_FILE_NAME = "marriage.tsv"
        private const val DIED_LIST_FILE_NAME = "died.tsv"
        private const val TAG_PARISH_REGISTER = "TAG_PARISH_REGISTER"
    }
}