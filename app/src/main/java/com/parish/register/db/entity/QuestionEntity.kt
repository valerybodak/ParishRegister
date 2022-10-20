package com.parish.register.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    var localId: Long = 0L,
    var id: String,
    var difficulty: Int? = 0,
    var category: String? = null,
    var text: String = ""
)