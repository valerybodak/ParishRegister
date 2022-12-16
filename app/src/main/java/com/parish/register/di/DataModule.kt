package com.parish.register.di

import android.app.Application
import androidx.room.Room
import com.parish.register.db.AppDatabase
import com.parish.register.db.dao.BornDao
import com.parish.register.db.dao.DiedDao
import com.parish.register.db.dao.MarriageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        const val DATABASE_NAME = "parish_register_database.db"
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Application): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDaoBorn(db: AppDatabase): BornDao {
        return db.bornDao()
    }

    @Singleton
    @Provides
    fun provideDaoMarriage(db: AppDatabase): MarriageDao {
        return db.marriageDao()
    }

    @Singleton
    @Provides
    fun provideDaoDied(db: AppDatabase): DiedDao {
        return db.diedDao()
    }
}