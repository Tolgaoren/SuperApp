package com.toren.features_note.ui.note_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.note.Note
import com.toren.domain.use_case.note.GetNoteByIdUseCase
import com.toren.domain.use_case.note.InsertNoteUseCase
import com.toren.domain.util.getCurrentFormattedTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
) : ViewModel() {

    private val _saveState = MutableStateFlow(false)
    val saveState: StateFlow<Boolean> = _saveState

    private val _noteState = MutableStateFlow(NoteDetailUiState())
    val noteState: StateFlow<NoteDetailUiState> = _noteState

    init {
    }

    fun onEvent(event: NoteDetailUiEvent) {
        when (event) {
            is NoteDetailUiEvent.SaveNote -> {
                if (event.title.isBlank() && event.content.isBlank()) {
                    return
                } else {
                    val note = Note(
                        id = _noteState.value.note!!.id,
                        title = event.title,
                        content = event.content,
                        timestamp = getCurrentFormattedTimestamp()
                    )
                    saveNote(note)
                }
            }

            is NoteDetailUiEvent.GetNoteById -> {
                Log.d("GetNoteById", "Getting note by ID: ${event.id}")
                getNoteById(event.id)
            }
        }
    }

    private fun saveNote(note: Note) {
        insertNoteUseCase(note).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("SaveNote", "Saving note...")
                }

                is Resource.Success -> {
                    _saveState.value = true
                    Log.d("SaveNote", "Note saved with ID: ${result.data}")
                }

                is Resource.Error -> {
                    _saveState.value = false
                    Log.e("SaveNote", "Error saving note: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getNoteById(id: Int) {
        getNoteByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _noteState.value = NoteDetailUiState(isLoading = true)
                }
                is Resource.Success -> {
                    _noteState.value = NoteDetailUiState(note = result.data)
                }
                is Resource.Error -> {
                    _noteState.value = NoteDetailUiState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }
}