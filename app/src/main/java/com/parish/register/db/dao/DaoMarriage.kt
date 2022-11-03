package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.MarriageEntity

@Dao
abstract class DaoMarriage {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: MarriageEntity): Long

    @Update
    abstract suspend fun update(item: MarriageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<MarriageEntity>): LongArray

    @Query("SELECT * FROM marriages")
    abstract suspend fun getAllMarriages(): List<MarriageEntity>

    @Query("SELECT * FROM marriages WHERE localId == :localId")
    abstract suspend fun getMarriageByLocalId(localId: String): MarriageEntity?

    @Query("DELETE FROM marriages")
    abstract suspend fun deleteAllMarriages()
}