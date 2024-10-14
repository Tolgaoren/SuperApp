package com.toren.data.repository

import com.toren.data.local.dao.NoteDao
import com.toren.data.local.entity.toNote
import com.toren.data.local.entity.toNoteEntity
import com.toren.domain.model.note.Note
import com.toren.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl
@Inject constructor(
    private val noteDao: NoteDao,
) : NoteRepository {
    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun getNotes(): List<Note> {
        return noteDao.getNotes().map { it.toNote() }
    }

    override suspend fun getNoteById(id: Int): Note {
        return noteDao.getNoteById(id).toNote()
    }

    override suspend fun deleteNotes(ids: List<Int>): Int {
        return noteDao.deleteNotes(ids)
    }
}