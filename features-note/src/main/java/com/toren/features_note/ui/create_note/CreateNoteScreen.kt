package com.toren.features_note.ui.create_note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun CreateNoteScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateNoteViewModel = hiltViewModel(),
    navController: NavController
) {

    val saveState by viewModel.saveState.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    BackHandler {
        viewModel.onEvent(CreateNoteUiEvent.SaveNote(
            title = title,
            content = content
        ))
    }

    LaunchedEffect(saveState) {
        if (saveState) {
            navController.popBackStack()
        }
    }
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    textStyle = TextStyle(
                        fontSize = 25.sp
                    ),
                    placeholder = {
                        Text(
                            text = "Title",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    textStyle = TextStyle(
                        fontSize = 17.sp,
                        textAlign = TextAlign.Start
                    ),
                    placeholder = {
                        Text(
                            text = "Content",
                            fontSize = 17.sp,
                            textAlign = TextAlign.Start
                        )
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )
            }

            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(
                        CreateNoteUiEvent.SaveNote(
                            title = title,
                            content = content
                        )
                    )
                },
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.BottomEnd),
                )
            {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Save button"
                )
            }
        }
    }
}