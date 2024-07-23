package com.example.tracker.Common.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.track3dlibrary.Common.BaseViewModel
import com.example.tracker.Common.Database.UserEntity
import com.example.tracker.Presentation.ExpenseScreen.ExpenseScreen
import com.example.tracker.Presentation.UserScreen.UserScreen
import kotlin.reflect.typeOf

@Composable
fun Navigation(baseViewModel: BaseViewModel.Companion) {


    val context = LocalContext.current

    val navController = rememberNavController()
    baseViewModel.setNavController(navController)
    NavHost(navController = navController, startDestination =NavigationRoute.UserScreen()){
        composable<NavigationRoute.UserScreen>{

            UserScreen()
        }
        composable<NavigationRoute.ExpenseScreen>(
            typeMap = mapOf(typeOf<UserEntity>() to userEntity )
        ){
            val args=it.toRoute<NavigationRoute.ExpenseScreen>()
            val userEntity=args.userEntity
            ExpenseScreen(userEntity = userEntity)
        }
    }
}