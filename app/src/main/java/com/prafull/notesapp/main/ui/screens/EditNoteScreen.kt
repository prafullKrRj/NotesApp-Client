package com.prafull.notesapp.main.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.prafull.notesapp.main.domain.models.NoteItem

@Composable
fun EditNoteScreen(noteItem: NoteItem, navController: NavController, viewModel: HomeViewModel) {
    NoteCard(note = noteItem, navController = navController)
}