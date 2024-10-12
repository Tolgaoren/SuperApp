package com.toren.features_note.ui.note_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.note.Note
import com.toren.domain.use_case.note.DeleteNotesUseCase
import com.toren.domain.use_case.note.GetNotesUseCase
import com.toren.domain.util.toFormatedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState

    private val _selectedNotes = mutableStateOf(setOf<Note>())
    val selectedNotes: State<Set<Note>> = _selectedNotes

    private val _isItemsSelectable = mutableStateOf(false)
    val isItemsSelectable: State<Boolean> = _isItemsSelectable

    init {
        getNotes()
    }

    fun onEvent(event: NoteListUiEvent) {
        when (event) {
            is NoteListUiEvent.Refresh -> {
                getNotes()
            }

            is NoteListUiEvent.OnNoteSelected -> {
                if (_selectedNotes.value.contains(event.note).not()) {
                    _selectedNotes.value += event.note
                } else {
                    _selectedNotes.value -= event.note
                }
                println(_selectedNotes.value.toString())
            }

            is NoteListUiEvent.SelectionModeChanged -> {
                _isItemsSelectable.value = !_isItemsSelectable.value
                if (_isItemsSelectable.value.not()) {
                    _selectedNotes.value = emptySet()
                    println(_selectedNotes.value.toString())
                }
            }

            is NoteListUiEvent.OnNotesDeleted -> {
                deleteNotes(_selectedNotes.value.map { it.id })
            }
        }
    }

    private fun getNotes() {
        getNotesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = NoteListUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = NoteListUiState(
                        notes = formatNotes(result.data?.sortedByDescending {
                            it.timestamp
                        } ?: emptyList()))
                }

                is Resource.Error -> {
                    _uiState.value = NoteListUiState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteNotes(ids: List<Int>) {
        deleteNotesUseCase(ids).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.e("NoteListViewModel", "deleteNote: Deleting notes...")
                }

                is Resource.Success -> {
                    getNotes()
                }

                is Resource.Error -> {
                    Log.e("NoteListViewModel", "deleteNote: ${result.message}")
                }

            }
        }.launchIn(viewModelScope)
    }


    private fun formatNotes(notes: List<Note>): List<Note> {
        return notes.map { note ->
            note.copy(timestamp = note.timestamp.toFormatedDate())
        }
    }
}