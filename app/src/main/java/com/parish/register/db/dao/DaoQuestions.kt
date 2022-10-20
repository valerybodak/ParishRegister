package com.parish.register.db.dao

import androidx.room.*
import com.parish.register.db.entity.QuestionEntity
import com.parish.register.db.entity.QuestionEntityWithAnswers

@Dao
abstract class DaoQuestions {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: QuestionEntity): Long

    @Update
    abstract suspend fun update(item: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<QuestionEntity>): LongArray

    @Query("DELETE FROM questions")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM questions")
    abstract suspend fun getAllQuestions(): List<QuestionEntityWithAnswers>

    @Query("SELECT * FROM questions WHERE id == :questionId")
    abstract suspend fun getQuestion(questionId: String): QuestionEntity?
}