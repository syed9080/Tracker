package com.example.tracker.Presentation.ExpenseScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracker.Common.Database.UserEntity
import com.example.tracker.Presentation.Components.AddExpenseDialog
import com.example.tracker.Presentation.Components.ExpenseCard
import com.example.tracker.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(userEntity: UserEntity) {


    val context = LocalContext.current

    val expenseScreenViewModel: ExpenseViewModel = hiltViewModel()

    val state = expenseScreenViewModel.state.collectAsState().value

    val scrollState = rememberScrollState()

    // Fetch expenses for the current month and year when the screen is first composed
    var currentDate by remember { mutableStateOf(Date()) }
    var currentMonthYear = SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(currentDate)
    LaunchedEffect(userEntity) {
        expenseScreenViewModel.send(
            ExpenseAction.fetchExpense(
                userId = userEntity.userId,
                month = currentMonthYear
            )
        )
    }
// show expenseAddDialog
    if (state.showDialog) {
        AddExpenseDialog(onSave = {
            Toast.makeText(
                context,
                "Save${it.date},${it.amount},${it.description}",
                Toast.LENGTH_SHORT
            ).show()
            expenseScreenViewModel.send(
                action = ExpenseAction.addExpenseToDb(
                    userId = userEntity.userId.toInt(),
                    date = it.date,
                    amount = it.amount.toDouble(),
                    description = it.description,
                    entryType = it.entryType
                )
            )
            expenseScreenViewModel.send(action = ExpenseAction.hideDialog)

        },
            onCancel = {
                expenseScreenViewModel.send(ExpenseAction.hideDialog)
            })

    }

    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    Text(
                        text = "User Name:${userEntity.userName}",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                title = {
                    Text(text = "Expense Screen")
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Your onClick action here
                    expenseScreenViewModel.send(ExpenseAction.showDialog)
                },
                modifier = Modifier
                    .padding(10.dp),
                shape = CircleShape,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = "total button",
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        },

        ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .verticalScroll(scrollState)
        ) {
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight()
                )
                {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            // Decrement the month by 1
                            currentDate = Calendar.getInstance().apply {
                                time = currentDate
                                add(Calendar.MONTH, -1)
                            }.time
                            currentMonthYear =
                                SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(currentDate)
                            expenseScreenViewModel.send(
                                ExpenseAction.fetchExpense(
                                    userId = userEntity.userId,
                                    month = currentMonthYear
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                contentDescription = "previous"
                            )
                        }
                        Box() {
                            Text("Month ${currentMonthYear}")
                        }
                        IconButton(onClick = {
                            // Increment the month by 1
                            currentDate = Calendar.getInstance().apply {
                                time = currentDate
                                add(Calendar.MONTH, 1)
                            }.time
                            currentMonthYear =
                                SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(currentDate)
                            expenseScreenViewModel.send(
                                ExpenseAction.fetchExpense(
                                    userId = userEntity.userId,
                                    month = currentMonthYear
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                                contentDescription = "Next"
                            )
                        }

                    }
                }
                Box()
                {
                    Text("Income:${state.monthIncome}  Expense:${state.monthExpense}")
                }
            }
            if (state.expenseList.isNotEmpty()) {
                state.expenseList.forEach { dayExpense ->
                    ExpenseCard(
                        expenseList = dayExpense.value
                    )
                }

                Spacer(modifier = Modifier.padding(bottom = 8.dp))

            }

//            if (state.userList.isNotEmpty()) {
//                state.userList.forEach {userEntity ->
//                    UserCard(
//                        userId = userEntity.userId,
//                        userName = userEntity.userName,
//                        phoneNumber = userEntity.phoneNumber,
//                        address = userEntity.address,
//                        modifier = Modifier,
//                        onClick = {
//                            userId=it
//                        }
//                    )
//                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
//                }
//            }

        }
    }
}