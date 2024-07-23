package com.example.tracker.Presentation.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tracker.Data.expenseData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    onCancel:(Boolean) -> Unit,
    onSave:(expenseData) -> Unit
) {

    var date by remember {
        mutableStateOf("")
    }
    var amount by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var type by remember {
        mutableStateOf("")
    }
    var entryType by remember {
        mutableStateOf(0)
    }


    var amountDouble by remember { mutableStateOf(0.0) }
    var error by remember { mutableStateOf<String?>(null) }
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            Button(onClick = {
                onSave(expenseData(date =date, amount = amount, entryType = entryType, description = description))
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onCancel(true)
            }) {
                Text(text = "Cancel")

            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    modifier = Modifier.padding(4.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                        amountDouble = it.toDoubleOrNull() ?: 0.0
                        error = if (amountDouble == 0.0 && it.isNotEmpty()) "Invalid number" else null
                    },
                    label = { Text("Amount") },
                    modifier = Modifier.padding(4.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = error != null
                )
                error?.let {
                    Text(text = it, color = Color.Red, modifier = Modifier.padding(start = 8.dp))
                }
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.padding(4.dp)
                )

                var expand by remember {
                    mutableStateOf(false)
                }
                ExposedDropdownMenuBox(expanded = expand, onExpandedChange = {
                    expand = !expand
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)) {
                    OutlinedTextField(
                        value = type, onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        singleLine = true,
                        placeholder = { Text(text = "Entry type")},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        textStyle = TextStyle.Default.copy(color = Color.White), // Customize the text color for disabled state
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black, // Customize the text color for disabled state
                            disabledBorderColor = Color.Gray // Customize the border color for disabled state
                        ),
                    )

                    ExposedDropdownMenu(expanded = expand, onDismissRequest = { expand=false }) {
                        DropdownMenuItem(text = { Text(text = "Income") }, onClick = {
                            type="Income"
                            entryType=1
                            expand=false })
                        DropdownMenuItem(text = { Text(text = "Expense") }, onClick = {
                            type="Expense"
                            entryType=0
                            expand=false })
                    }

                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun AddExpenseDialogPreview() {
    AddExpenseDialog(
        onCancel = { /*TODO*/ },
        onSave = { /*TODO*/ }
    )
}