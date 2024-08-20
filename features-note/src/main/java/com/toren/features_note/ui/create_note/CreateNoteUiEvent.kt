package com.toren.features_note.ui.create_note

sealed class CreateNoteUiEvent {
    data class SaveNote(val title: String, val content: String): CreateNoteUiEvent()
}