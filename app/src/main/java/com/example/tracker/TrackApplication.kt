package com.example.tracker

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tracker.Common.Database.Expense
import com.example.tracker.Common.Database.ExpenseDao
import com.example.tracker.Common.Database.UserDao
import com.example.tracker.Common.Database.UserEntity
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TrackApplication : Application() {



    @Database(entities = [UserEntity::class,Expense::class], version = 2)
    abstract class AppDatabase : RoomDatabase() {
        abstract val UserDao: UserDao
        abstract val ExpenseDao:ExpenseDao
    }


}
