package com.parish.register.model

data class Born(
    val localId: Long = 0L,
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
    val createdDate: String
) : ListItem {

    override fun getSortDate(): String = birthDate

    override fun getSortName(): String = fullName
}