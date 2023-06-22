package com.parish.register.model

data class Died(
    val localId: Long = 0L,
    val fundNumber: String,
    val inventoryNumber: String,
    val caseNumber: String,
    val page: String,
    val deathDate: String,
    val burialDate: String,
    val gender: Gender,
    val fullName: String,
    val parents: String,
    val causeOfDeath: String,
    val comments: String,
    val createdDate: String
) : RegisterItem {

    override fun getSortDate(): String = deathDate

    override fun getSortName(): String = fullName
}