package com.parish.register.ui.inspector

import com.parish.register.model.RegisterItem

data class DuplicateItem(
    /**
     * Similarity is a number within 0 and 1)
     */
    val similarity: Double = 0.0,
    val item1: RegisterItem,
    val item2: RegisterItem
)