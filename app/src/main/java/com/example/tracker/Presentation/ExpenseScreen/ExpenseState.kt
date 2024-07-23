package com.example.tracker.Presentation.ExpenseScreen

import com.example.track3dlibrary.Common.BaseState
import com.example.tracker.Common.Database.Expense

data class ExpenseState(
    val expenseList: Map<String, List<Expense>> = emptyMap(),
    val showDialog: Boolean = false,
    val monthIncome:Double = 0.0,
    val monthExpense:Double = 0.0
):BaseState{
    override fun initialState(): BaseState {
        TODO("Not yet implemented")
    }

}