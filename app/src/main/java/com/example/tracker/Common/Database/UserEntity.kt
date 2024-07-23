package com.example.tracker.Common.Database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "UserTable")
@Parcelize
@Serializable
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId:Int=0,
    val userName:String="Default",
    val phoneNumber:String="0000000000",
    val address:String="Default"
) : Parcelable
