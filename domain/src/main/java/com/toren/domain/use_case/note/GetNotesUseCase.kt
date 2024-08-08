package com.toren.domain.use_case.note

import com.toren.domain.Resource
import com.toren.domain.model.note.Note
import com.toren.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<Resource<List<Note>>> = flow {
        emit(Resource.Loading())
        try {
            val notes = repository.getNotes()
            emit(Resource.Success(notes))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}