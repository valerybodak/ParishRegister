package com.parish.register.utils

import java.util.*

fun String.toLowerCaseLocalized(): String = toLowerCase(Locale.getDefault())

fun String.containsIgnoreCase(stringToCompare: String): Boolean = toLowerCaseLocalized().contains(stringToCompare.toLowerCaseLocalized())
