package com.parish.register.common

import com.parish.register.db.entity.BornEntity
import com.parish.register.db.entity.DiedEntity
import com.parish.register.db.entity.MarriageEntity
import com.parish.register.model.Born
import com.parish.register.model.Died
import com.parish.register.model.Gender
import com.parish.register.model.Marriage

fun String.toBornEntity(): BornEntity? {
    val items = split("\t")
    if(items.size >= 15) {
        //check for Int because we have a headers in the TSV file in the first string
        items[1].toIntOrNull()?.let {
            return BornEntity(
                archiveId = items[0],
                fundNumber = it.toString(),
                inventoryNumber = items[2],
                caseNumber = items[3],
                page = items[4],
                gender = if (items[5].isNotEmpty()) Gender.MALE else if (items[6].isNotEmpty()) Gender.FEMALE else Gender.UNKNOWN,
                birthDate = items[7],
                baptismDate = items[8],
                fullName = items[9],
                parents = items[10],
                godParents = items[11],
                priest = items[12],
                comments = items[13],
                createdDate = items[14]
            )
        }
    }
    return null
}

fun BornEntity.toBorn() = Born(
    localId = localId,
    archiveId = archiveId,
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
    createdDate = createdDate
)

fun String.toMarriageEntity(): MarriageEntity? {
    val items = split("\t")
    //check for Int because we have a headers in the TSV file in the first string
    items[0].toIntOrNull()?.let {
        return MarriageEntity(
            fundNumber = items[0],
            inventoryNumber = items[1],
            caseNumber = items[2],
            page = items[3],
            marriageNumber = items[4],
            date = items[5],
            groom = items[6],
            bride = items[7],
            witness1 = items[8],
            witness2 = items[9],
            priest = items[10],
            comments = items[11],
            createdDate = items[12]
        )
    }
    return null
}

fun MarriageEntity.toMarriage() = Marriage(
    localId = localId,
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
    createdDate = createdDate
)

fun String.toDiedEntity(): DiedEntity? {
    val items = split("\t")
    //check for Int because we have a headers in the TSV file in the first string
    items[0].toIntOrNull()?.let {
        return DiedEntity(
            fundNumber = items[0],
            inventoryNumber = items[1],
            caseNumber = items[2],
            page = items[3],
            gender = if (items[4].isNotEmpty()) Gender.MALE else if (items[5].isNotEmpty()) Gender.FEMALE else Gender.UNKNOWN,
            deathDate = items[6],
            burialDate = items[7],
            fullName = items[8],
            parents = items[9],
            causeOfDeath = items[10],
            comments = items[11],
            createdDate = items[12]
        )
    }
    return null
}

fun DiedEntity.toDied() = Died(
    localId = localId,
    fundNumber = fundNumber,
    inventoryNumber = inventoryNumber,
    caseNumber = caseNumber,
    page = page,
    deathDate = deathDate,
    burialDate = burialDate,
    gender = gender,
    fullName = fullName,
    parents = parents,
    causeOfDeath = causeOfDeath,
    comments = comments,
    createdDate = createdDate
)