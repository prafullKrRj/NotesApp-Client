package com.prafull.notesapp.main.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.prafull.notesapp.main.domain.models.NoteItem

@Composable
fun EditNoteScreen(noteItem: NoteItem, navController: NavController, viewModel: HomeViewModel) {
    val state = rememberRichTextState()
    state.setMarkdown(noteItem.content)
    RichTextEditor(state = state, modifier = Modifier.fillMaxSize())
}