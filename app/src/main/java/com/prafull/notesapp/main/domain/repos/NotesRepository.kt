package com.prafull.notesapp.main.domain.repos

import com.prafull.notesapp.main.domain.models.CreateNoteItem
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.managers.BaseClass
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getAllNotes(token: String): Flow<BaseClass<List<NoteItem>>>
    suspend fun deleteNote(token: String, noteId: String): Flow<BaseClass<List<NoteItem>>>
    suspend fun createNote(token: String, note: CreateNoteItem): Flow<BaseClass<List<NoteItem>>>
    suspend fun updateNote(token: String, note: NoteItem): Flow<BaseClass<List<NoteItem>>>
    suspend fun getNoteById(token: String, noteId: String): Flow<BaseClass<NoteItem>>
}