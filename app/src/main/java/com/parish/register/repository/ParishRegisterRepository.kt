package com.parish.register.repository

import com.parish.register.common.*
import com.parish.register.db.dao.DaoBorn
import com.parish.register.db.dao.DaoDied
import com.parish.register.db.dao.DaoMarriage
import com.parish.register.model.Born
import com.parish.register.model.Died
import com.parish.register.model.Marriage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParishRegisterRepository @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val daoBorn: DaoBorn,
    private val daoMarriage: DaoMarriage,
    private val daoDied: DaoDied
) {

    fun getBornList(observeProgress: Boolean = false): Flow<Resource<List<Born>>> {
        return FirebaseHelper.loadFileData(
            BORN_LIST_FILE_NAME,
            query = { daoBorn.getAllBorn().map { it.toBorn() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_BORN_LIST)) },
            observeProgress = observeProgress,
            saveFetchResponse = { rawItems -> saveBornList(rawItems) }
        )
    }

    private suspend fun saveBornList(rawItems: List<String>) {
        daoBorn.deleteAllBorn()
        rawItems.forEach { line ->
            line.toBornEntity()?.let { remoteItem ->
                daoBorn.insert(remoteItem)
            }
        }
        sharedPrefsManager.saveLastSynced(TAG_BORN_LIST)
    }

    fun getMarriageList(observeProgress: Boolean = false): Flow<Resource<List<Marriage>>> {
        return FirebaseHelper.loadFileData(
            MARRIAGE_LIST_FILE_NAME,
            query = { daoMarriage.getAllMarriages().map { it.toMarriage() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_MARRIAGE_LIST)) },
            observeProgress = observeProgress,
            saveFetchResponse = { rawItems -> saveMarriageList(rawItems) }
        )
    }

    private suspend fun saveMarriageList(rawItems: List<String>) {
        daoMarriage.deleteAllMarriages()
        rawItems.forEach { line ->
            line.toMarriageEntity()?.let { remoteItem ->
                daoMarriage.insert(remoteItem)
            }
        }
        sharedPrefsManager.saveLastSynced(TAG_MARRIAGE_LIST)
    }

    fun getDiedList(observeProgress: Boolean = false): Flow<Resource<List<Died>>> {
        return FirebaseHelper.loadFileData(
            DIED_LIST_FILE_NAME,
            query = { daoDied.getAllDied().map { it.toDied() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_DIED_LIST)) },
            observeProgress = observeProgress,
            saveFetchResponse = { rawItems -> saveDiedList(rawItems) }
        )
    }

    private suspend fun saveDiedList(rawItems: List<String>) {
        daoDied.deleteAllDied()
        rawItems.forEach { line ->
            line.toDiedEntity()?.let { remoteItem ->
                daoDied.insert(remoteItem)
            }
        }
        sharedPrefsManager.saveLastSynced(TAG_DIED_LIST)
    }

    companion object {
        private const val BORN_LIST_FILE_NAME = "born.tsv"
        private const val MARRIAGE_LIST_FILE_NAME = "marriage.tsv"
        private const val DIED_LIST_FILE_NAME = "died.tsv"

        private const val TAG_BORN_LIST = "TAG_BORN_LIST"
        private const val TAG_MARRIAGE_LIST = "TAG_MARRIAGE_LIST"
        private const val TAG_DIED_LIST = "TAG_DIED_LIST"
    }
}