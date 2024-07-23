package com.example.tracker.Common.Database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(
    tableName = "Expense", foreignKeys = [
        ForeignKey(entity = UserEntity::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE)
    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int = 0,
    val userId: Int,
    val date: String,  // Storing date as a String in "yyyy-MM-dd" format
    val amount: Double,
    val description: String,
    val entryType: Int=0 //0 for expense and 1 for income
) : Parcelable
