package com.parish.register.model

data class Died(
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
    val createdDate: String
) : ListItem {

    override fun getSortDate(): String = deathDate

    override fun getSortName(): String = fullName
}