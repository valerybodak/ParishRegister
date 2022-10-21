package com.parish.register.model

data class Born(
    val localId: Long = 0L,
    val id: String,
    val fundNumber: String,
    val inventoryNumber: String,
    val caseNumber: String,
    val page: String,
    val birthDate: String,
    val baptismDate: String,
    val gender: String,
    val fullName: String,
    val parents: String,
    val godParents: String,
    val priest: String,
    val comments: String,
    val archiveVisitDate: String
): ListItem