package com.toren.features_alarm.ui.create_alarm


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(
    viewModel: CreateAlarmViewModel = hiltViewModel()
) {

    val timeState = rememberTimePickerState()
    val calendar = Calendar.getInstance()
    calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            timeState.hour,
            timeState.minute
        )

    val alarm = calendar.timeInMillis
    /*val date = Date(alarm)
    val format = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.getDefault())
    val formattedDate = format.format(date)
    println(formattedDate)*/


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TimePicker(state = timeState)
        Text(text = alarm.toString())
        Button(onClick = {
          viewModel.onEvent(CreateAlarmUiEvent.CreateAlarm(
              time = alarm,
              message = "Alarm"
          ))
        }) {
            Text(text = "Save")
        }


    }
}
