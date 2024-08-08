package com.toren.domain.use_case.note

import com.toren.domain.Resource
import com.toren.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
){
    operator fun invoke(id: Int): Flow<Resource<Int>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.deleteNote(id)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}