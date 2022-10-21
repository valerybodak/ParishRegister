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

    @Query("SELECT * FROM marriage")
    abstract suspend fun getAllMarriages(): List<MarriageEntity>

    @Query("SELECT * FROM marriage WHERE id == :id")
    abstract suspend fun getMarriage(id: String): MarriageEntity?
}