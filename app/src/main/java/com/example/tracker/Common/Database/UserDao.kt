package com.example.tracker.Common.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM UserTable")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT DISTINCT userId FROM usertable")
    suspend fun getUserId(): List<Int>
}