package com.parish.register.di

import android.app.Application
import com.parish.register.db.dao.BornDao
import com.parish.register.common.SharedPrefsManager
import com.parish.register.db.dao.DiedDao
import com.parish.register.db.dao.MarriageDao
import com.parish.register.repository.ParishRegisterRepository
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

    @Singleton
    @Provides
    fun provideParishRegisterRepository(
        sharedPrefsManager: SharedPrefsManager,
        daoBorn: BornDao,
        daoMarriage: MarriageDao,
        daoDied: DiedDao
    ): ParishRegisterRepository {
        return ParishRegisterRepository(sharedPrefsManager, daoBorn, daoMarriage, daoDied)
    }
}