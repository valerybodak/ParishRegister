package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.BornEntity

@Dao
interface DaoBorn {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BornEntity): Long

    @Update
    suspend fun update(item: BornEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<BornEntity>): LongArray

    @Query("SELECT * FROM born")
    suspend fun getAllBorn(): List<BornEntity>

    @Query("SELECT * FROM born WHERE localId == :localId")
    suspend fun getBornByLocalId(localId: String): BornEntity?

    @Query("DELETE FROM born")
    suspend fun deleteAllBorn()

    @Query("SELECT COUNT(localId) FROM born")
    suspend fun getBornCount(): Int

}