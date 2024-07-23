package com.example.tracker.Presentation.UserScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.track3dlibrary.Common.BaseViewModel
import com.example.tracker.Common.Database.ExpenseDao
import com.example.tracker.Common.Database.UserDao
import com.example.tracker.Common.Database.UserEntity
import com.example.tracker.Common.Navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val userDao: UserDao,
    private val expenseDao: ExpenseDao
) : BaseViewModel<UserScreenState, UserAction>() {
    override val initialState: UserScreenState = UserScreenState()

    private val _State = MutableStateFlow(initialState)
    val state: StateFlow<UserScreenState> = _State.asStateFlow()

    init {
        send(UserAction.fetchUser)
    }


    override suspend fun reduce(action: UserAction): UserScreenState? {
        when (action) {
            UserAction.showDialog -> {
                _State.value = _State.value.copy(showDialog = true)
            }

            UserAction.hideDialog -> {
                _State.value = _State.value.copy(showDialog = false)
            }

            is UserAction.addUserToDb -> {
                userDao.insertUser(
                    UserEntity(
                        userName = action.userName,
                        phoneNumber = action.phoneNumber,
                        address = action.address
                    )
                )
                send(UserAction.refreshDb)
            }

            UserAction.fetchUser -> {
                fetchUser()
            }

            UserAction.refreshDb -> {
                fetchUser()
            }

            UserAction.showExpenseDialog -> {
                _State.value = _State.value.copy(showExpenseDialog = true)
            }

            is UserAction.goToExpnseScreen -> {
                navigateTo(NavigationRoute.ExpenseScreen(action.userEntity))

            }

            is UserAction.getIncome -> TODO()
            is UserAction.getTotal -> TODO()
        }

        return null
    }


    private fun fetchUser() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                var totalAmount:MutableMap<Int,Double?> = mutableMapOf()
                var totalIncome:MutableMap<Int,Double?> = mutableMapOf()
                val userList = userDao.getAllUsers()
                val userId = userDao.getUserId()
                userId.forEach { id ->
                    totalAmount[id] = expenseDao.getTotal(id)
                    totalIncome.put(id,expenseDao.getTotalIncome(id))
                }
                Log.d("total", "total income:${totalIncome},total amount:${totalAmount}")
                _State.value = _State.value.copy(
                    userList = userList,
                    totalAmount = totalAmount,
                    totalIncome = totalIncome
                )
            }
        }
    }

}