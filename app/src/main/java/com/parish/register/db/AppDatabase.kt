package com.parish.register.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parish.register.db.DatabaseVersions.LAST_VERSION
import com.parish.register.db.dao.DaoBorn
import com.parish.register.db.entity.BornEntity

@Database(
    entities = [
        BornEntity::class],
    version = LAST_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoBorn(): DaoBorn
}