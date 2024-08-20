@file:OptIn(ExperimentalMaterial3Api::class)

package com.toren.features_alarm.ui.create_alarm


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(
    viewModel: CreateAlarmViewModel = hiltViewModel(),
    navController: NavController,
) {
    val saveState by viewModel.saveState.collectAsState()
    val timeState = rememberTimePickerState()
    val dateState = rememberDatePickerState()
    var title by remember { mutableStateOf("") }
    var openDatePicker by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(saveState) {
        if (saveState) {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TimePicker(state = timeState)

        IconButton(
            onClick = { openDatePicker = true },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Date icon"
            )
        }

        TextField(
            value = title,
            onValueChange = { title = it },
            textStyle = TextStyle(
                fontSize = 17.sp
            ),
            placeholder = {
                Text(
                    text = "Alarm",
                    fontSize = 17.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f)
            ) {
                Text(text = "Cancel")
            }

            OutlinedButton(
                onClick = {
                    viewModel.onEvent(
                        CreateAlarmUiEvent.CreateAlarm(
                            hour = timeState.hour,
                            minute = timeState.minute,
                            message = title,
                            date = dateState.selectedDateMillis
                        )
                    )
                },
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(1f)
            ) {
                Text(text = "Save")
            }
        }
        if (openDatePicker) {
            DatePickerDialog(
                onDismissRequest = { openDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDatePicker = false
                        },
                        enabled = dateState.selectedDateMillis != null
                    ) {
                        Text(text = "Confirm")
                        println(dateState.selectedDateMillis)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { openDatePicker = false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        {
            DatePicker(state = dateState)
        }
    }
}


}
