package com.prafull.notesapp.main.data

import com.prafull.notesapp.main.domain.models.CreateNoteItem
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.main.domain.repos.NotesRepository
import com.prafull.notesapp.managers.BaseClass
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NotesRepositoryImpl(
    private val apiService: ApiService
) : NotesRepository {
    override suspend fun getAllNotes(token: String): Flow<BaseClass<List<NoteItem>>> {
        return callbackFlow {
            try {
                val response = apiService.getAllNotes(token)
                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(BaseClass.Success(it.toList()))
                    } ?: trySend(BaseClass.Error("Error fetching notes"))
                } else {
                    trySend(BaseClass.Error("Error fetching notes"))
                }
            } catch (e: Exception) {
                trySend(BaseClass.Error("Error fetching notes"))
            }
            awaitClose { }
        }
    }

    override suspend fun deleteNote(
        token: String,
        noteId: String
    ): Flow<BaseClass<List<NoteItem>>> {
        return callbackFlow {
            try {
                val response = apiService.deleteNote(token, noteId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(BaseClass.Success(it.toList()))
                    } ?: trySend(BaseClass.Error("Error deleting note"))
                } else {
                    trySend(BaseClass.Error("Error deleting note"))
                }
            } catch (e: Exception) {
                trySend(BaseClass.Error("Error deleting note"))
            }
            awaitClose { }
        }
    }

    override suspend fun createNote(
        token: String,
        note: CreateNoteItem
    ): Flow<BaseClass<List<NoteItem>>> {
        return callbackFlow {
            try {
                val response = apiService.createNote(token, note)
                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(BaseClass.Success(it.toList()))
                    } ?: trySend(BaseClass.Error("Error creating note"))
                } else {
                    trySend(BaseClass.Error("Error creating note"))
                }
            } catch (e: Exception) {
                trySend(BaseClass.Error("Error creating note"))
            }
            awaitClose { }
        }
    }

    override suspend fun updateNote(
        token: String,
        note: NoteItem
    ): Flow<BaseClass<List<NoteItem>>> {
        return callbackFlow {
            try {
                val response = apiService.updateNote(token, note)
                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(BaseClass.Success(it.toList()))
                    } ?: trySend(BaseClass.Error("Error updating note"))
                } else {
                    trySend(BaseClass.Error("Error updating note"))
                }
            } catch (e: Exception) {
                trySend(BaseClass.Error("Error updating note"))
            }
            awaitClose { }
        }
    }

    override suspend fun getNoteById(token: String, noteId: String): Flow<BaseClass<NoteItem>> {
        return callbackFlow {
            try {
                val response = apiService.getNoteById(token, noteId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(BaseClass.Success(it))
                    } ?: trySend(BaseClass.Error("Error fetching note"))
                } else {
                    trySend(BaseClass.Error("Error fetching note"))
                }
            } catch (e: Exception) {
                trySend(BaseClass.Error("Error fetching note"))
            }
            awaitClose { }
        }
    }

}