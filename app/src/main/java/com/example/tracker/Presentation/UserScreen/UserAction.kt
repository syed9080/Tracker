package com.example.tracker.Presentation.UserScreen

import com.example.track3dlibrary.Common.BaseAction
import com.example.tracker.Common.Database.UserEntity

sealed class UserAction : BaseAction {
    data object showDialog : UserAction()
    data object hideDialog : UserAction()
    data class addUserToDb(val userName: String, val phoneNumber: String, val address: String) : UserAction()
    data object fetchUser : UserAction()
    data object refreshDb : UserAction()
    data object showExpenseDialog : UserAction()
    data class goToExpnseScreen(val userEntity: UserEntity) : UserAction()
    data class getTotal(val userId:Int) : UserAction()
    data class getIncome(val userId:Int) : UserAction()
}