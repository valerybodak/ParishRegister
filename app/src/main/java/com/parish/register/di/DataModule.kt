package com.parish.register.di

import android.app.Application
import androidx.room.Room
import com.parish.register.db.AppDatabase
import com.parish.register.db.dao.DaoAnswers
import com.parish.register.db.dao.DaoQuestions
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
    fun provideDaoQuestions(db: AppDatabase): DaoQuestions {
        return db.daoQuestions()
    }

    @Singleton
    @Provides
    fun provideDaoAnswers(db: AppDatabase): DaoAnswers {
        return db.daoAnswers()
    }
}