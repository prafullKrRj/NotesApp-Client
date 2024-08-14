package com.prafull.notesapp.main.ui.screens.createNote

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.notesapp.main.domain.models.CreateNoteItem
import com.prafull.notesapp.main.domain.repos.NotesRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateNoteVM(
    context: Context
) : ViewModel(), KoinComponent {

    private val token =
        context.getSharedPreferences("notes_pref", Context.MODE_PRIVATE).getString("token", "")
            ?: ""
    private val repo by inject<NotesRepository>()
    var note by mutableStateOf(CreateNoteItem("", ""))

    fun saveNote() {
        viewModelScope.launch {
            repo.createNote(token, note).collectLatest {
                Log.d("CreateNoteVM", "saveNote: $it")
            }
        }
    }
}