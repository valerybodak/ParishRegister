package com.parish.register.repository

import com.parish.register.common.*
import com.parish.register.db.dao.DaoBorn
import com.parish.register.db.dao.DaoMarriage
import com.parish.register.model.ListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParishRegisterRepository @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val daoBorn: DaoBorn,
    private val daoMarriage: DaoMarriage
) {

    fun getBornList(observeProgress: Boolean = false): Flow<Resource<List<ListItem>>> {
        return FirebaseHelper.loadFileData(
            BORN_LIST_FILE_NAME,
            query = { daoBorn.getAllBorn().map { it.toBorn() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_BORN_LIST)) },
            observeProgress = observeProgress,
            saveFetchResponse = { rawItems -> saveBornList(rawItems) }
        )
    }

    private suspend fun saveBornList(rawItems: List<String>) {
        rawItems.forEach { line ->
            line.toBornEntity()?.let { remoteItem ->
                val localItem = daoBorn.getBorn(remoteItem.id)
                if (localItem != null) {
                    daoBorn.update(remoteItem.copy(localId = localItem.localId))
                } else {
                    daoBorn.insert(remoteItem)
                }
            }
        }
        sharedPrefsManager.saveLastSynced(TAG_BORN_LIST)
    }

    fun getMarriageList(observeProgress: Boolean = false): Flow<Resource<List<ListItem>>> {
        return FirebaseHelper.loadFileData(
            MARRIAGE_LIST_FILE_NAME,
            query = { daoMarriage.getAllMarriages().map { it.toMarriage() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_MARRIAGE_LIST)) },
            observeProgress = observeProgress,
            saveFetchResponse = { rawItems -> saveMarriageList(rawItems) }
        )
    }

    private suspend fun saveMarriageList(rawItems: List<String>) {
        rawItems.forEach { line ->
            line.toMarriageEntity()?.let { remoteItem ->
                val localItem = daoMarriage.getMarriage(remoteItem.id)
                if (localItem != null) {
                    daoMarriage.update(remoteItem.copy(localId = localItem.localId))
                } else {
                    daoMarriage.insert(remoteItem)
                }
            }
        }
        sharedPrefsManager.saveLastSynced(TAG_MARRIAGE_LIST)
    }

    companion object {
        private const val BORN_LIST_FILE_NAME = "born.tsv"
        private const val MARRIAGE_LIST_FILE_NAME = "marriage.tsv"
        private const val DIED_LIST_FILE_NAME = "died.tsv"

        private const val TAG_BORN_LIST = "TAG_BORN_LIST"
        private const val TAG_MARRIAGE_LIST = "TAG_MARRIAGE_LIST"
    }
}