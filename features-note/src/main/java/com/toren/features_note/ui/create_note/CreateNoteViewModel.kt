package com.toren.features_note.ui.create_note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.note.Note
import com.toren.domain.use_case.note.InsertNoteUseCase
import com.toren.domain.util.getCurrentFormattedTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
) : ViewModel() {

    private val _saveState = MutableStateFlow(false)
    val saveState: StateFlow<Boolean> = _saveState

    fun onEvent(event: CreateNoteUiEvent) {
        when (event) {
            is CreateNoteUiEvent.SaveNote -> {
                if (event.title.isBlank() && event.content.isBlank()) {
                    return
                } else {
                    val note = Note(
                        title = event.title,
                        content = event.content,
                        timestamp = getCurrentFormattedTimestamp()
                    )
                    saveNote(note)
                }
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
                    Log.e("SaveNote", "Error saving note: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }
}