package com.prafull.notesapp.main.ui.screens.editNoteScreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.notesapp.main.domain.models.NoteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class EditNoteViewModel(
    private val note: NoteItem,
    context: Context
) : ViewModel(), KoinComponent {
    private val token =
        context.getSharedPreferences("notes", Context.MODE_PRIVATE).getString("token", "") ?: ""


    private val finalNote by mutableStateOf(note)
    var editableNote by mutableStateOf(note)

    fun updateNote() {

    }

    fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}