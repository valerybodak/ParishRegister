package com.parish.register.ui.inspector

import com.parish.register.model.RegisterItem

data class DuplicateItem(
    val item1: RegisterItem,
    val item2: RegisterItem
)