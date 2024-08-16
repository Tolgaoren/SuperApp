package com.toren.domain.repository

import com.toren.domain.model.note.Note

interface NoteRepository {
    suspend fun insertNote(note: Note): Long

    suspend fun getNotes(): List<Note>

    suspend fun deleteNote(id: Int): Int

    suspend fun getNoteById(id: Int): Note
}