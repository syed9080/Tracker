package com.example.tracker.Presentation.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tracker.Common.Database.Expense


@Composable
fun ExpenseCard(expenseList: List<Expense>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    )
    {
        Column {
                // header contain date and Day income and balance
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .border(border = BorderStroke(1.dp, color = Color.Black))
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    {
                        Text(expenseList[0].date)
                    }
                    Box()
                    {
                        Text("Day income and balance")
                    }
                }
                // list of balance and income
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                {
                    Column {
                        expenseList.forEach{singleExpense->
                            CardContent(expense = singleExpense)
                        }
                    }

                }
        }

    }


}

@Composable
private fun CardContent(expense: Expense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .border(border = BorderStroke(1.dp, Color.White)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.fillMaxWidth(fraction = 0.25f))
        {
            Text(expense.date, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
        Box(modifier = Modifier.fillMaxWidth(fraction = .7f)) {
            Text(expense.description)
        }
        Box()
        {
            Text(expense.amount.toString())
        }
    }
}

//
//@Preview
//@Composable
//fun ExpenseCardPreview() {
//    ExpenseCard()
//}