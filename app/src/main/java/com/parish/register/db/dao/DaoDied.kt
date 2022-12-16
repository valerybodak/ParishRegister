package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.DiedEntity

@Dao
interface DaoDied {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DiedEntity): Long

    @Update
    suspend fun update(item: DiedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DiedEntity>): LongArray

    @Query("SELECT * FROM died")
    suspend fun getAllDied(): List<DiedEntity>

    @Query("SELECT * FROM died WHERE localId == :localId")
    suspend fun getDiedByLocalId(localId: String): DiedEntity?

    @Query("DELETE FROM died")
    suspend fun deleteAllDied()

    @Query("SELECT COUNT(localId) FROM died")
    suspend fun getDiedCount(): Int
}