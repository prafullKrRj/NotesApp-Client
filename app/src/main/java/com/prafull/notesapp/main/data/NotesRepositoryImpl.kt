package com.prafull.notesapp.main.data

import android.util.Log
import com.prafull.notesapp.main.domain.models.CreateNoteItem
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.main.domain.repos.NotesRepository
import com.prafull.notesapp.managers.BaseClass
import com.prafull.notesapp.utils.CryptoEncryption
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
                    response.body()?.let { notes ->
                        val decryptedNotes = notes.map { note ->
                            note.copy(
                                title = CryptoEncryption.decrypt(note.title),
                                content = CryptoEncryption.decrypt(note.content)
                            )
                        }
                        trySend(BaseClass.Success(decryptedNotes))
                    } ?: {
                        Log.d("NotesRepositoryImpl", "getAllNotes: ${response.errorBody()}")
                        trySend(BaseClass.Error("Error fetching notes"))
                    }
                } else {
                    Log.d("NotesRepositoryImpl", "getAllNotes: ${response.errorBody()}")
                    trySend(BaseClass.Error("Error fetching notes"))
                }
            } catch (e: Exception) {
                Log.d("NotesRepositoryImpl", "getAllNotes: ${e.message}")
                trySend(BaseClass.Error("Error fetching notes"))
            }
            awaitClose { }
        }
    }

    override suspend fun deleteNote(
        token: String, noteId: String
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
        token: String, note: CreateNoteItem
    ): Flow<BaseClass<List<NoteItem>>> {
        return callbackFlow {
            try {
                Log.d("NotesRepositoryImpl", "createNote: $note")
                Log.d("NotesRepositoryImpl", "createNote: ${CryptoEncryption.encrypt(note.title)}")
                val encryptedNote = note.copy(
                    title = CryptoEncryption.encrypt(note.title),
                    content = CryptoEncryption.encrypt(note.content)
                )
                Log.d("NotesRepositoryImpl", "createNote: $note")
                val response = apiService.createNote(token, encryptedNote)
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
        token: String, note: UpdateNoteItem
    ): Flow<BaseClass<NoteItem>> {
        return callbackFlow {
            try {
                val encryptedNote = note.copy(
                    title = CryptoEncryption.encrypt(note.title),
                    content = CryptoEncryption.encrypt(note.content)
                )
                val response = apiService.updateNote(
                    token = token, note = encryptedNote
                )
                Log.d("NotesRepositoryImpl", "updateNote: $note")
                Log.d("NotesRepositoryImpl", "updateNote: $response")
                if (response.isSuccessful) {
                    response.body()?.let {
                        trySend(BaseClass.Success(it))
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
                    response.body()?.let { note ->
                        val decryptedNote = note.copy(
                            title = CryptoEncryption.decrypt(note.title),
                            content = CryptoEncryption.decrypt(note.content)
                        )
                        trySend(BaseClass.Success(decryptedNote))
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

    override suspend fun deleteManyNotes(
        token: String, noteIds: List<String>
    ): Flow<BaseClass<List<NoteItem>>> {
        return callbackFlow {
            try {
                Log.d("NotesRepositoryImpl", "deleteManyNotes: $noteIds")
                val response = apiService.deleteManyNotes(token, DeleteNoteBody(noteIds))
                Log.d("NotesRepositoryImpl", "deleteManyNotes: ${response}")
                if (response.isSuccessful) {
                    Log.d("NotesRepositoryImpl", "deleteManyNotes: ${response.body()}")
                    response.body()?.let {
                        trySend(BaseClass.Success(it.toList()))
                    } ?: trySend(BaseClass.Error("Error deleting notes"))
                } else {
                    trySend(BaseClass.Error("Error deleting notes"))
                }
            } catch (e: Exception) {
                trySend(BaseClass.Error("Error deleting notes"))
            }
            awaitClose { }
        }
    }
}