package com.parish.register.utils

import java.util.*

fun Date.parseYear(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal[Calendar.YEAR]
}