package com.example.tracker.Presentation.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tracker.R

@Composable
fun UserCard(
    userName: String,
    phoneNumber: String,
    address: String,
    modifier: Modifier,
    userId: Int,
    totalIncome: Map<Int, Double?>,
    totalAmount: Map<Int, Double?>,
    onClick: (Boolean) -> Unit
) {

    val context = LocalContext.current
    val incomeColor = Color(0xFF4CAF50)
    val totalColor = Color(0xFF2196F3)
    val balanceColor = Color(0xFFF44336)
    val textColor = Color(0xFF333333)
    val borderColor = Color(0xFFEF5360)
    val name by remember {
        mutableStateOf(userName)
    }
    val phone by remember {
        mutableStateOf(phoneNumber)
    }
    val totalTransaction by remember {
        mutableStateOf("$ ${totalAmount.getValue(userId) ?: 0.0}")
    }
    val income by remember {
        mutableStateOf("$${totalIncome.getValue(userId) ?: 0.0}")
    }
    val balance by remember {
        mutableStateOf("$ ${(totalIncome.getValue(userId)
            ?.let {totalAmount.getValue(userId)?.minus(it)}) ?: totalAmount.getValue(userId) ?:0.0}")
    }
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFFE5E5E5)),
                    start = Offset.Zero,
                    end = Offset.Infinite
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                border = BorderStroke(1.5.dp, color = borderColor),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onClick(true)
            },

        )
    {
        Column(modifier = Modifier.padding(8.dp)) {


            //Total income content
            Box(modifier = Modifier.fillMaxWidth())
            {
                Column(modifier = Modifier.align(alignment = Alignment.TopEnd)) {

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.total),
                            contentDescription = "income image",
                            modifier = Modifier.size(35.dp)
                        )
                        Column {
                            Text(text = "Total", textAlign = TextAlign.Center, color = totalColor)
                            Text(text = totalTransaction, color = totalColor)
                        }

                    }

                }
            }
            //Name and phone number content here
            Box(modifier = Modifier.fillMaxWidth())
            {
                Column(modifier = Modifier) {
                    Text(
                        text = name,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(text = phone, color = textColor, fontWeight = FontWeight.Bold)
                }
            }

            //income and balance here
            Box(modifier = Modifier.fillMaxWidth())
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.income),
                                contentDescription = "income image",
                                modifier = Modifier.size(35.dp)
                            )
                            Column {
                                Text(
                                    text = "Income",
                                    textAlign = TextAlign.Center,
                                    color = incomeColor
                                )
                                Text(
                                    text = income,
                                    textAlign = TextAlign.Center,
                                    color = incomeColor
                                )
                            }
                        }

                    }
                    Column {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.balance),
                                contentDescription = "balance image",
                                modifier = Modifier.size(35.dp)
                            )
                            Column {
                                Text(
                                    text = "Balance",
                                    textAlign = TextAlign.Center,
                                    color = balanceColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = balance,
                                    textAlign = TextAlign.Center,
                                    color = balanceColor,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                }
            }


        }
    }


}