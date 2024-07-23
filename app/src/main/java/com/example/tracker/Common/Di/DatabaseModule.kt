package com.example.tracker.Common.Di

import android.content.Context
import androidx.room.Room
import com.example.tracker.Common.Database.ExpenseDao
import com.example.tracker.Common.Database.UserDao
import com.example.tracker.TrackApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TrackApplication.AppDatabase {
        return Room.databaseBuilder(
            appContext,
            TrackApplication.AppDatabase::class.java,
            "Tracker-DB"
        ).fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideUsersDao(appDatabase: TrackApplication.AppDatabase): UserDao {
        return appDatabase.UserDao
    }

    @Provides
    @Singleton
    fun provideExpenseDao(appDatabase: TrackApplication.AppDatabase): ExpenseDao {
        return appDatabase.ExpenseDao
    }
}
