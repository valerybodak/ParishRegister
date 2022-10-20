package com.parish.register.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parish.register.db.DatabaseVersions.LAST_VERSION
import com.parish.register.db.dao.DaoAnswers
import com.parish.register.db.dao.DaoQuestions
import com.parish.register.db.entity.AnswerEntity
import com.parish.register.db.entity.QuestionEntity

@Database(
    entities = [
        QuestionEntity::class,
        AnswerEntity::class],
    version = LAST_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoQuestions(): DaoQuestions
    abstract fun daoAnswers(): DaoAnswers
}