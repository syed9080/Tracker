package com.example.tracker.Common.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expense: Expense)

//    @Query("SELECT * FROM Expense WHERE userId = :userId AND date LIKE :month || '%' ORDER BY date")
//    suspend fun getExpensesForMonth(userId: Int, month: String): List<Expense>

    @Query("SELECT * FROM Expense WHERE userId = :userId AND (date LIKE '%' || :month ) ORDER BY date")
    suspend fun getExpensesForMonth(userId: Int, month: String): List<Expense>

   @Query("SELECT SUM(amount) FROM Expense WHERE userId = :userId AND (date LIKE '%' || :month) AND entryType =1 ")
   suspend fun getMonthIncome(userId: Int,month: String):Double

    @Query("SELECT SUM(amount) FROM Expense WHERE userId = :userId AND (date LIKE '%' || :month) AND entryType =0 ")
    suspend fun getMonthExpense(userId: Int,month: String):Double

    @Query("SELECT SUM(amount) FROM Expense WHERE userId = :userId AND (date LIKE '%' || :month)")
    suspend fun getMonthTotal(userId: Int,month: String):Double

    @Query("SELECT SUM(amount) FROM Expense WHERE userId = :userId AND entryType =1 ")
    suspend fun getTotalIncome(userId: Int):Double

    @Query("SELECT SUM(amount) FROM Expense WHERE userId = :userId AND entryType =0")
    suspend fun getTotal(userId: Int):Double



}