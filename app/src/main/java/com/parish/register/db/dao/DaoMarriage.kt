package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.MarriageEntity

@Dao
interface DaoMarriage {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MarriageEntity): Long

    @Update
    suspend fun update(item: MarriageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MarriageEntity>): LongArray

    @Query("SELECT * FROM marriages")
    suspend fun getAllMarriages(): List<MarriageEntity>

    @Query("SELECT * FROM marriages WHERE localId == :localId")
    suspend fun getMarriageByLocalId(localId: String): MarriageEntity?

    @Query("DELETE FROM marriages")
    suspend fun deleteAllMarriages()

    @Query("SELECT COUNT(localId) FROM marriages")
    suspend fun getMarriageCount(): Int
}