package com.example.tracker.Common.Navigation

import com.example.tracker.Common.Database.UserEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    @Serializable
    data class UserScreen(val name: String="syed") : NavigationRoute()


    @Serializable
    data class ExpenseScreen(val userEntity: UserEntity) : NavigationRoute()
}