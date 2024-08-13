package com.prafull.notesapp.main.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.main.domain.repos.NotesRepository
import com.prafull.notesapp.managers.BaseClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class HomeViewModel(
    context: Context,
    private val repo: NotesRepository
) : ViewModel(), KoinComponent {
    private val pref = context.getSharedPreferences("notes_pref", Context.MODE_PRIVATE)
    private val _uiState = MutableStateFlow<BaseClass<List<NoteItem>>>(BaseClass.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllNotes()
    }

    fun filterNotes(query: String) {

        viewModelScope.launch {
            val allNotes = (uiState.value as? BaseClass.Success<List<NoteItem>>)?.data ?: return@launch
            val filteredNotes = allNotes.filter {
                it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true)
            }

            _uiState.update { BaseClass.Success(filteredNotes) }
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            repo.getAllNotes("Bearer ${pref.getString("token", "")}").collect { resp ->
                _uiState.update {
                    resp
                }
            }
        }
    }

    fun deleteNoteById(noteId: String) {
        viewModelScope.launch {
            repo.deleteNote("Bearer ${pref.getString("token", "")}", noteId).collect { resp ->
                _uiState.update {
                    resp
                }
            }
        }
    }

    fun createNote(noteItem: NoteItem) {
        viewModelScope.launch {
            repo.createNote("Bearer ${pref.getString("token", "")}", noteItem).collect { resp ->
                _uiState.update {
                    resp
                }
            }
        }
    }

    fun updateNote(noteItem: NoteItem) {
        viewModelScope.launch {
            repo.updateNote("Bearer ${pref.getString("token", "")}", noteItem).collect { resp ->
                _uiState.update {
                    resp
                }
            }
        }
    }
}