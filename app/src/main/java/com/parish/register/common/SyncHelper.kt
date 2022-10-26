package com.parish.register.common

import java.util.*

object SyncHelper {

    private const val SYNC_INTERVAL_MILLIS = 3600000 //1 hour

    fun isSyncNeed(lastSync: Long): Boolean {
        return (Calendar.getInstance().timeInMillis - lastSync) > SYNC_INTERVAL_MILLIS
    }
}