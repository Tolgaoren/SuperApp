package com.toren.domain.use_case.note

import com.toren.domain.Resource
import com.toren.domain.model.note.Note
import com.toren.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(note: Note): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.insertNote(note)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}