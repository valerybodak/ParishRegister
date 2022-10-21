package com.parish.register.model

data class Born(
    val localId: Long = 0L,
    val id: String,
    val fundNumber: String,
    val inventoryNumber: String,
    val caseNumber: String
): ListItem