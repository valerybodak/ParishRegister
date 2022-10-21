package com.parish.register.model

data class Marriage(
    val localId: Long = 0L,
    val id: String,
    val fundNumber: String,
    val inventoryNumber: String,
    val caseNumber: String,
    val page: String,
    val date: String,
    val marriageNumber: String,
    val groom: String,
    val bride: String,
    val witness1: String,
    val witness2: String,
    val priest: String,
    val comments: String,
    val archiveVisitDate: String
): ListItem