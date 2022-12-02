package com.parish.register.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.parish.register.db.DatabaseVersions.LAST_VERSION
import com.parish.register.db.converters.RoomConverters
import com.parish.register.db.dao.DaoBorn
import com.parish.register.db.dao.DaoDied
import com.parish.register.db.dao.DaoMarriage
import com.parish.register.db.entity.BornEntity
import com.parish.register.db.entity.DiedEntity
import com.parish.register.db.entity.MarriageEntity

@Database(
    entities = [
        BornEntity::class,
        MarriageEntity::class,
        DiedEntity::class],
    version = LAST_VERSION
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoBorn(): DaoBorn
    abstract fun daoMarriage(): DaoMarriage
    abstract fun daoDied(): DaoDied
}