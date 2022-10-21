package com.parish.register.common

import com.parish.register.db.entity.BornEntity
import com.parish.register.model.Born

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