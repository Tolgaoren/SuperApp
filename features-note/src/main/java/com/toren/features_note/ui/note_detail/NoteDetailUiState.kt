package com.toren.features_note.ui.note_detail

import com.toren.domain.model.note.Note

data class NoteDetailUiState(
    val note: Note? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
