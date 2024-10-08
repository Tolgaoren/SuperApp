package com.toren.features_note.ui.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.model.note.Note
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState

    init {
        getNotes()
    }

    fun onEvent(event: NoteListUiEvent) {
        when (event) {
            is NoteListUiEvent.Refresh -> {
                getNotes()
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


    private fun formatNotes(notes: List<Note>): List<Note> {
        return notes.map { note ->
            note.copy(timestamp = note.timestamp.toFormatedDate())
        }
    }
}