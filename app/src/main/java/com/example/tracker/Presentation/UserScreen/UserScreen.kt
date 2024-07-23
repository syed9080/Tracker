package com.example.tracker.Presentation.UserScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracker.Presentation.Components.AlertDialog
import com.example.tracker.Presentation.Components.UserCard
import com.example.tracker.Presentation.UserScreen.UserAction.hideDialog
import com.example.tracker.Presentation.UserScreen.UserAction.showDialog
import com.example.tracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen() {

    val TAG = "USERSCREEN"

    val context = LocalContext.current
    val userScreenViewModel: UserScreenViewModel = hiltViewModel()
    var userId by rememberSaveable {
        mutableStateOf("")
    }
    val state = userScreenViewModel.state.collectAsState().value
    val scrollState = rememberScrollState()

    if (state.showDialog) {
        AlertDialog(onSave = {
            Toast.makeText(
                context,
                "Save${it.first},${it.second},${it.third}",
                Toast.LENGTH_SHORT
            ).show()
            userScreenViewModel.send(
                action = UserAction.addUserToDb(
                    userName = it.first,
                    phoneNumber = it.second,
                    address = it.third
                )
            )
            userScreenViewModel.send(action = hideDialog)

        },
            onCancel = {
                userScreenViewModel.send(hideDialog)
            })
    }
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    Text(
                        text = "Total Users:${state.userList.size}",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                title = {
                    Text(text = "User Screen")
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
                    userScreenViewModel.send(showDialog)
                },
                modifier = Modifier
                    .padding(10.dp),
                shape = CircleShape,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.useradd),
                    contentDescription = "user add button",
                    modifier = Modifier
                        .size(60.dp),
                )
            }
        },

        ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .verticalScroll(scrollState)
        ) {
            if (state.userList.isNotEmpty()) {
                state.userList.forEach {userEntity ->
                    UserCard(
                        userId = userEntity.userId,
                        userName = userEntity.userName,
                        phoneNumber = userEntity.phoneNumber,
                        address = userEntity.address,
                        modifier = Modifier,
                        totalIncome = state.totalIncome,
                        totalAmount = state.totalAmount,
                        onClick = {
                            userScreenViewModel.send(UserAction.goToExpnseScreen(userEntity=userEntity))
                        }
                    )
                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                }
            }

        }
    }

}






