package com.prafull.notesapp.main.ui.screens.editNoteScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.notesapp.main.data.UpdateNoteItem
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.main.domain.repos.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditNoteViewModel(
    private val passedNote: NoteItem, context: Context
) : ViewModel(), KoinComponent {
    private val token =
        context.getSharedPreferences("notes_pref", Context.MODE_PRIVATE).getString("token", "")
            ?: ""
    private val repo by inject<NotesRepository>()

    private val finalNote by mutableStateOf(passedNote)

    var note by mutableStateOf(
        UpdateNoteItem(
            title = passedNote.title,
            content = passedNote.content,
            id = passedNote._id
        )
    )

    fun updateNote() {
        Log.d("EditNoteViewModel", "updateNote: ${note}")
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateNote(token, note).collectLatest {

            }
        }
    }

    fun setFinalNote() {
        note = UpdateNoteItem(
            title = passedNote.title,
            content = passedNote.content,
            id = passedNote._id
        )
    }

    fun getRealNote() = finalNote
    fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}