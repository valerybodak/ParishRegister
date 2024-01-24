package com.parish.register.model

data class Born(
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
) : RegisterItem {

    override fun getSortDate(): String = birthDate

    override fun getSortName(): String = fullName
}