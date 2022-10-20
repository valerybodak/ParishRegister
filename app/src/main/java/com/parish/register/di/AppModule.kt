package com.parish.register

import android.app.Application
import com.parish.register.nineteen.common.SharedPrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefsManager(
        context: Application
    ): SharedPrefsManager {
        return SharedPrefsManager(context)
    }

    /*@Singleton
    @Provides
    fun provideQuizRepository(
        sharedPrefsManager: SharedPrefsManager,
        daoQuestions: DaoQuestions,
        daoAnswers: DaoAnswers
    ): QuizRepository {
        return QuizRepository(sharedPrefsManager, daoQuestions, daoAnswers)
    }*/
}