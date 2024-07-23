package com.example.track3dlibrary.Common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

import com.example.tracker.Common.Navigation.NavigationRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable



abstract class BaseViewModel<state : BaseState, Action : BaseAction> : ViewModel() {

    private val baseTag = "BaseViewModel"
    companion object {
        private val _navController = MutableStateFlow<NavHostController?>(null)
        val navController = _navController.asStateFlow()

        fun setNavController(navHostController: NavHostController) {
            _navController.value = navHostController
        }

        fun navigateTo(route : NavigationRoute) {
            CoroutineScope(Dispatchers.Main).launch {
                navController.value?.navigate(route)
            }
        }

        fun navigateBack(){
            CoroutineScope(Dispatchers.Main).launch {
                navController.value?.popBackStack()
            }
        }

        fun navigateToWithPopBackStack(route : NavigationRoute, popUpTo : NavigationRoute) {
            CoroutineScope(Dispatchers.Main).launch {
                navController.value?.navigate(route){
                    popUpTo(popUpTo) {
                        inclusive = true
                    }
                }
            }
        }
    }

    abstract val initialState : state

    private val _baseState = MutableStateFlow(initialState)
    val baseState = _baseState.asStateFlow()

    fun send(action : Action ) {
        CoroutineScope(Dispatchers.Default).launch {
            Log.e(baseTag, "Send - event: $action")
            val newState = reduce(action)
            _baseState.value = newState ?: _baseState.value
        }

    }
    abstract suspend fun reduce(action : Action) : state?
}