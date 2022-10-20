package com.parish.register.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionEntityWithAnswers(
    @Embedded
    val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answers: List<AnswerEntity>?
)