package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.AnswerEntity

@Dao
abstract class DaoAnswers {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: AnswerEntity): Long

    @Update
    abstract suspend fun update(item: AnswerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<AnswerEntity>): LongArray

    @Query("DELETE FROM answers WHERE questionId = :questionId")
    abstract suspend fun deleteAnswersForQuestion(questionId: String)
}