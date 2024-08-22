package com.toren.features_alarm.ui.alarm_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.toren.domain.model.alarm.Alarm
import com.toren.features_alarm.ui.graphs.AlarmScreens

@Composable
fun AlarmListScreen(
    navController: NavHostController,
    viewModel: AlarmListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(AlarmListUiEvent.Refresh)
    }

    Surface {

        Column {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Alarm",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,

                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp)
                )
                IconButton(
                    onClick = {
                        navController.navigate(AlarmScreens.CreateAlarm.route)
                    },
                    modifier = Modifier.padding(end = 20.dp, top = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        "New reminder action button."
                    )
                }
            }

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = uiState.error.toString())
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    items(uiState.alarms) { alarm ->
                        AlarmItem(
                            alarm = alarm,
                            onClick = {
                                navController.navigate(
                                    AlarmScreens.AlarmDetail.route + "/${alarm.id}"
                                )
                            },
                            isSwitchChecked = alarm.enabled,
                            onSwitchChange = {
                                viewModel.onEvent(
                                    AlarmListUiEvent.ReverseAlarmState(alarm.id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlarmItem(
    alarm: Alarm,
    onClick: () -> Unit,
    isSwitchChecked: Boolean,
    onSwitchChange: (Boolean) -> Unit,
) {
    val hour = alarm.time.split("\n").getOrNull(0)
    val date = alarm.time.split("\n").getOrNull(1)
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = CardDefaults.elevatedShape,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hour ?: "",
                    fontSize = 20.sp,
                )
                Text(
                    text = date ?: "",
                    fontSize = 15.sp,
                )
            }
            Switch(
                checked = isSwitchChecked,
                onCheckedChange = {
                    onSwitchChange(it)
                }
            )
        }
    }
}

