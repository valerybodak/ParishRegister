package com.parish.register.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "born")
data class BornEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,
    val id: String,
    val fund: String,
    val inventory: String,
    val volume: String
)