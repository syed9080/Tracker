package com.example.tracker.Presentation.ExpenseScreen

import com.example.track3dlibrary.Common.BaseAction
import com.example.tracker.Presentation.UserScreen.UserAction

sealed class ExpenseAction:BaseAction {
    data object  showDialog:ExpenseAction()
    data object  hideDialog:ExpenseAction()
    data class fetchExpense(val userId: Int,val month:String) : ExpenseAction()
    data class addExpenseToDb(
        val userId: Int,
        val date: String,
        val amount: Double,
        val description: String,
        val entryType: Int
    ) : ExpenseAction()
}