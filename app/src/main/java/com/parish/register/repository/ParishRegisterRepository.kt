package com.parish.register.repository

import com.parish.register.common.*
import com.parish.register.db.dao.BornDao
import com.parish.register.db.dao.DiedDao
import com.parish.register.db.dao.MarriageDao
import com.parish.register.model.Born
import com.parish.register.model.Died
import com.parish.register.model.Marriage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParishRegisterRepository @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val daoBorn: BornDao,
    private val daoMarriage: MarriageDao,
    private val daoDied: DiedDao
) {

    fun getBornListFlow(
        forceSync: Boolean = false,
        observeProgress: Boolean = false
    ): Flow<Resource<List<Born>>> {
        return FirebaseHelper.loadFileData(
            BORN_LIST_FILE_NAME,
            query = { daoBorn.getAllBorn().map { it.toBorn() } },
            shouldFetch = {
                if (forceSync) true else SyncHelper.isSyncNeed(
                    sharedPrefsManager.getLastSynced(
                        TAG_BORN_LIST
                    )
                )
            },
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

    fun getMarriageListFlow(
        forceSync: Boolean = false,
        observeProgress: Boolean = false
    ): Flow<Resource<List<Marriage>>> {
        return FirebaseHelper.loadFileData(
            MARRIAGE_LIST_FILE_NAME,
            query = { daoMarriage.getAllMarriages().map { it.toMarriage() } },
            shouldFetch = {
                if (forceSync) true else SyncHelper.isSyncNeed(
                    sharedPrefsManager.getLastSynced(
                        TAG_MARRIAGE_LIST
                    )
                )
            },
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

    fun getDiedListFlow(
        forceSync: Boolean = false,
        observeProgress: Boolean = false
    ): Flow<Resource<List<Died>>> {
        return FirebaseHelper.loadFileData(
            DIED_LIST_FILE_NAME,
            query = { daoDied.getAllDied().map { it.toDied() } },
            shouldFetch = {
                if (forceSync) true else SyncHelper.isSyncNeed(
                    sharedPrefsManager.getLastSynced(
                        TAG_DIED_LIST
                    )
                )
            },
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

    suspend fun getBornList(): List<Born> {
        return daoBorn.getAllBorn().map { it.toBorn() }
    }

    suspend fun getMarriageList(): List<Marriage> {
        return daoMarriage.getAllMarriages().map { it.toMarriage() }
    }

    suspend fun getDiedList(): List<Died> {
        return daoDied.getAllDied().map { it.toDied() }
    }

    suspend fun getBornCount(): Int {
        return daoBorn.getBornCount()
    }

    suspend fun getMarriageCount(): Int {
        return daoMarriage.getMarriageCount()
    }

    suspend fun getDiedCount(): Int {
        return daoDied.getDiedCount()
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