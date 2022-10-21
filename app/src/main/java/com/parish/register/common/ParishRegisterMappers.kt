package com.parish.register.common

import com.parish.register.db.entity.BornEntity
import com.parish.register.db.entity.MarriageEntity
import com.parish.register.model.Born
import com.parish.register.model.Marriage

fun String.toBornEntity(): BornEntity? {
    val items = split("\t")
    //check for Int because we have a headers in the TSV file in the first string
    items[0].toIntOrNull()?.let {
        return BornEntity(
            id = items[0],
            fundNumber = items[1],
            inventoryNumber = items[2],
            caseNumber = items[3],
            page = items[4],
            birthDate = items[7],
            baptismDate = items[8],
            gender = "m",
            fullName = items[9],
            parents = items[10],
            godParents = items[11],
            priest = items[12],
            comments = items[13],
            archiveVisitDate = items[14]
        )
    }
    return null
}

fun BornEntity.toBorn() = Born(
    localId = localId,
    id = id,
    fundNumber = fundNumber,
    inventoryNumber = inventoryNumber,
    caseNumber = caseNumber,
    page = page,
    birthDate = birthDate,
    baptismDate = baptismDate,
    gender = gender,
    fullName = fullName,
    parents = parents,
    godParents = godParents,
    priest = priest,
    comments = comments,
    archiveVisitDate = archiveVisitDate
)

fun String.toMarriageEntity(): MarriageEntity? {
    val items = split("\t")
    //check for Int because we have a headers in the TSV file in the first string
    items[0].toIntOrNull()?.let {
        return MarriageEntity(
            id = items[0],
            fundNumber = items[1],
            inventoryNumber = items[2],
            caseNumber = items[3],
            page = items[4],
            marriageNumber = items[5],
            date = items[6],
            groom = items[7],
            bride = items[8],
            witness1 = items[9],
            witness2 = items[10],
            priest = items[11],
            comments = items[12],
            archiveVisitDate = items[13]
        )
    }
    return null
}

fun MarriageEntity.toMarriage() = Marriage(
    localId = localId,
    id = id,
    fundNumber = fundNumber,
    inventoryNumber = inventoryNumber,
    caseNumber = caseNumber,
    page = page,
    date = date,
    marriageNumber = marriageNumber,
    groom = groom,
    bride = bride,
    witness1 = witness1,
    witness2 = witness2,
    priest = priest,
    comments = comments,
    archiveVisitDate = archiveVisitDate
)