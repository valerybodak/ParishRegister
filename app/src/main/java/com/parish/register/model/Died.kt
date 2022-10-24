package com.parish.register.model

data class Died(
    val localId: Long = 0L,
    val id: String,
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
): ListItem