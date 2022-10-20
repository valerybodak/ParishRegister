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

    fun getQuiz(category: Category = Category.ALL): Flow<Resource<List<ListItem>>> {
        return FirebaseHelper.loadFileData(
            BORN_LIST_FILE_NAME,
            query = { daoBorn.getAllBorn().map { it.toBorn() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_PARISH_REGISTER)) },
            observeProgress = true,
            saveFetchResponse = { rawItems -> saveQuiz(rawItems) }
        )
    }

    private suspend fun saveQuiz(rawItems: List<String>) {
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
        private const val BORN_LIST_FILE_NAME = "quiz-19.tsv"
        private const val TAG_PARISH_REGISTER = "TAG_PARISH_REGISTER"
    }
}