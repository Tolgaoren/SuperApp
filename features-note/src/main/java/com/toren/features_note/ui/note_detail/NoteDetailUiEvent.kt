package com.toren.features_note.ui.note_detail


sealed class NoteDetailUiEvent {
    data class SaveNote(
        val title: String,
        val content: String
    ): NoteDetailUiEvent()

    data class GetNoteById(
        val id: Int
    ): NoteDetailUiEvent()
}