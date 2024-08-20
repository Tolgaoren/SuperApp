package com.toren.features_note.ui.note_detail

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import com.toren.features_note.ui.components.NoteTemplate

@Composable
fun NoteDetailScreen(
    viewModel: NoteDetailViewModel = hiltViewModel(),
    navController: NavHostController,
    noteId: Int,
) {
    val saveState by viewModel.saveState.collectAsState()
    val noteState by viewModel.noteState.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(noteState.note) {
        noteState.note?.let { note ->
            title = note.title
            content = note.content
        }
    }

    LaunchedEffect(noteId) {
        viewModel.onEvent(
            NoteDetailUiEvent
                .GetNoteById(noteId)
        )
    }

    BackHandler {
        if (title.isNotBlank() || content.isNotBlank()) {
            viewModel.onEvent(
                NoteDetailUiEvent.SaveNote(
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
                NoteDetailUiEvent.SaveNote(
                    title = title,
                    content = content
                )
            )
        }
    )
}