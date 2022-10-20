package com.parish.register.di

import android.app.Application
import com.parish.register.db.dao.DaoBorn
import com.parish.register.common.SharedPrefsManager
import com.parish.register.repository.ParishRepository
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
    fun provideParishRepository(
        sharedPrefsManager: SharedPrefsManager,
        daoBorn: DaoBorn
    ): ParishRepository {
        return ParishRepository(sharedPrefsManager, daoBorn)
    }
}