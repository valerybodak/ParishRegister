package com.parish.register.nineteen.common

import java.util.*

object SyncHelper {

    private const val SYNC_INTERVAL_MILLIS = 3600 //1 hour

    fun isSyncNeed(lastSync: Long): Boolean {
        return (Calendar.getInstance().timeInMillis - lastSync) > SYNC_INTERVAL_MILLIS
    }
}