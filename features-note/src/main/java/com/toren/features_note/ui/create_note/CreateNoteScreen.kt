package com.toren.features_note.ui.create_note

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.toren.features_note.ui.components.NoteTemplate

@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteViewModel = hiltViewModel(),
    navController: NavController,
) {
    val saveState by viewModel.saveState.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    BackHandler {
        if (title.isNotBlank() || content.isNotBlank()) {
            viewModel.onEvent(
                CreateNoteUiEvent.SaveNote(
                    title = title,
                    content = content
                )
            )
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(saveState) {
        if (saveState) {
            navController.popBackStack()
        }
    }

    NoteTemplate(
        title = title,
        content = content,
        onTitleChange = { title = it },
        onContentChange = { content = it },
        onSave = {
            viewModel.onEvent(
                CreateNoteUiEvent.SaveNote(
                    title = title,
                    content = content
                )
            )
        }
    )
}