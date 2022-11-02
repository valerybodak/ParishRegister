package com.parish.register.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "died")
data class DiedEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,
    val fundNumber: String,
    val inventoryNumber: String,
    val caseNumber: String,
    val page: String,
    val deathDate: String,
    val burialDate: String,
    val gender: String,
    val fullName: String,
    val parents: String,
    val causeOfDeath: String,
    val comments: String,
    val archiveVisitDate: String
)