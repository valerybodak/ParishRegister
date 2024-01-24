package com.parish.register.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.parish.register.model.Gender

@Entity(tableName = "born")
data class BornEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,
    val archiveId: String,
    val fundNumber: String,
    val inventoryNumber: String,
    val caseNumber: String,
    val page: String,
    val birthDate: String,
    val baptismDate: String,
    val gender: Gender,
    val fullName: String,
    val parents: String,
    val godParents: String,
    val priest: String,
    val comments: String,
    val createdDate: String
)