package com.example.tracker.Presentation.ExpenseScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.track3dlibrary.Common.BaseViewModel
import com.example.tracker.Common.Database.Expense
import com.example.tracker.Common.Database.ExpenseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseDao: ExpenseDao
) : BaseViewModel<ExpenseState, ExpenseAction>() {

    override val initialState: ExpenseState = ExpenseState()

    private val _State = MutableStateFlow(initialState)
    val state: StateFlow<ExpenseState> = _State.asStateFlow()


    override suspend fun reduce(action: ExpenseAction): ExpenseState? {
        when (action) {
            ExpenseAction.showDialog -> {
                _State.value = _State.value.copy(showDialog = true)
            }

            ExpenseAction.hideDialog -> {
                _State.value = _State.value.copy(showDialog = false)
            }

            is ExpenseAction.addExpenseToDb -> {
                expenseDao.insertExpense(
                    Expense(
                        userId = action.userId,
                        date = action.date,
                        amount = action.amount,
                        description = action.description,
                        entryType = action.entryType
                    )
                )
            }

            is ExpenseAction.fetchExpense -> {
                val date = Date()
                val userId = action.userId
                val month = action.month                     //action.month.format("yyyy-MM")
                Log.d("CHECK", "$month")
                fetchExpense(userId = userId, month = month)
            }
        }
        return null
    }


    private fun fetchExpense(userId: Int, month: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val expenseList = expenseDao.getExpensesForMonth(userId = userId, month = month)
                val expensebydate = expenseList.groupBy { it.date }
                val monthIncome = expenseDao.getMonthIncome(userId = userId, month = month)
                val monthExpense = expenseDao.getMonthExpense(userId = userId, month = month)

                Log.d("group check", "$expensebydate")
                _State.value = _State.value.copy(
                    expenseList = expensebydate,
                    monthIncome = monthIncome,
                    monthExpense = monthExpense
                )
            }


        }
    }

}



