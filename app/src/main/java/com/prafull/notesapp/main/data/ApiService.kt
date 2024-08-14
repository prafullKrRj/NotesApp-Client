package com.prafull.notesapp.main.data

import com.prafull.notesapp.main.domain.models.CreateNoteModel
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.main.domain.models.Notes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/api/notes/getAll")
    suspend fun getAllNotes(
        @Header("Authorization") token: String
    ): Response<Notes>

    @POST("/api/notes/create")
    suspend fun createNote(
        @Header("Authorization") token: String,
        @Body note: CreateNoteModel
    ): Response<Notes>

    @POST("/api/notes/update")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Body note: NoteItem
    ): Response<Notes>

    @POST("/api/notes/delete/{noteId}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("noteId") noteId: String
    ): Response<Notes>

    @GET("/api/notes/get/{noteId}")
    suspend fun getNoteById(
        @Header("Authorization") token: String,
        @Path("noteId") noteId: String
    ): Response<NoteItem>
}