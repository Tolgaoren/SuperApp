package com.toren.domain.use_case.note

import com.toren.domain.Resource
import com.toren.domain.model.note.Note
import com.toren.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
){
    operator fun invoke(id: Int): Flow<Resource<Note>> = flow {
        emit(Resource.Loading())
        try {
            val note = repository.getNoteById(id)
            emit(Resource.Success(note))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}