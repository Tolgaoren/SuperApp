package com.toren.features_note.ui.create_note

import com.toren.domain.model.note.Note

sealed class CreateNoteUiEvent {
    data class SaveNote(val title: String, val content: String): CreateNoteUiEvent()
}