package com.example.tracker.Presentation.UserScreen

import com.example.track3dlibrary.Common.BaseState
import com.example.tracker.Common.Database.UserEntity

data class UserScreenState(
    val userList: List<UserEntity> = emptyList(),
    val showDialog: Boolean = false,
    val showExpenseDialog: Boolean = false,
    val totalAmount: MutableMap<Int, Double?> = mutableMapOf(),
    val totalIncome: MutableMap<Int, Double?> = mutableMapOf()
) : BaseState {
    override fun initialState(): BaseState {
        return UserScreenState()
    }

}