package com.toren.features_note.ui.note_list

import com.toren.domain.model.note.Note

data class NoteListUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
