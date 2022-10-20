package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.BornEntity

@Dao
abstract class DaoBorn {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: BornEntity): Long

    @Update
    abstract suspend fun update(item: BornEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<BornEntity>): LongArray
}