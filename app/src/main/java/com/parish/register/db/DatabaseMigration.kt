package com.parish.register.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseVersions {

    const val LAST_VERSION = 1
}

/*
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE words ADD COLUMN word_id INTEGER DEFAULT -1 NOT NULL")
        database.execSQL("ALTER TABLE words ADD COLUMN category TEXT")
    }
}*/
