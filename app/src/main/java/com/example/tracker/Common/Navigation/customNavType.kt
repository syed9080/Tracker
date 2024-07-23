package com.example.tracker.Common.Navigation


//
//val userEntity = object : NavType<UserEntity>(
//    isNullableAllowed = true
//){
//
//
//    override fun serializeAsValue(value: UserEntity): String {
//        return Json.encodeToString(value)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    override fun get(bundle: Bundle, key: String): UserEntity {
//
//        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            bundle.getParcelable(key, UserEntity::class.java) as UserEntity
//        }else{
//            bundle.getParcelable<UserEntity>(key) as UserEntity
//        }
//    }
//
//    override fun parseValue(value: String): UserEntity {
//        return Json.decodeFromString<UserEntity>(value)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: UserEntity) {
//        bundle.putParcelable(key, value)
//    }
//
//}


import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.example.tracker.Common.Database.UserEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val userEntity = object : NavType<UserEntity>(
    isNullableAllowed = true
) {

    override fun serializeAsValue(value: UserEntity): String {
        return Json.encodeToString(value)
    }
    override fun put(bundle: Bundle, key: String, value: UserEntity) {
        bundle.putParcelable(key, value)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(bundle: Bundle, key: String): UserEntity {

        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, UserEntity::class.java) as UserEntity
        }else{
            bundle.getParcelable<UserEntity>(key) as UserEntity
        }
    }

    override fun parseValue(value: String): UserEntity {
        return Json.decodeFromString<UserEntity>(value)
    }
}