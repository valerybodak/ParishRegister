package com.parish.register.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,
    val questionId: String,
    val text: String = "",
    val isCorrect: Boolean = false
)