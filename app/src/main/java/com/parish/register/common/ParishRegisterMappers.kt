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
            caseNumber = items[3]
        )
    }
    return null
}

fun BornEntity.toBorn() = Born(
    localId = localId,
    id = id,
    fundNumber = fundNumber,
    inventoryNumber = inventoryNumber,
    caseNumber = caseNumber
)