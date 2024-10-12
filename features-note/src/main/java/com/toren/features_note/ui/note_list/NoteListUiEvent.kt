package com.toren.features_note.ui.note_list

import com.toren.domain.model.note.Note

sealed class NoteListUiEvent {
    object Refresh : NoteListUiEvent()
    data class OnNoteSelected(val note: Note) : NoteListUiEvent()
    object SelectionModeChanged: NoteListUiEvent()
    object OnNotesDeleted : NoteListUiEvent()
}