package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.DiedEntity

@Dao
abstract class DaoDied {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: DiedEntity): Long

    @Update
    abstract suspend fun update(item: DiedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<DiedEntity>): LongArray

    @Query("SELECT * FROM died")
    abstract suspend fun getAllDied(): List<DiedEntity>

    @Query("SELECT * FROM died WHERE id == :id")
    abstract suspend fun getDied(id: String): DiedEntity?
}