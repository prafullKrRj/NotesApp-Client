package com.prafull.notesapp.main.data

import com.google.gson.annotations.SerializedName
import com.prafull.notesapp.main.domain.models.CreateNoteItem
import com.prafull.notesapp.main.domain.models.NoteItem
import com.prafull.notesapp.main.domain.models.Notes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/api/notes/getAll")
    suspend fun getAllNotes(
        @Header("Authorization") token: String
    ): Response<Notes>

    @POST("/api/notes/create")
    suspend fun createNote(
        @Header("Authorization") token: String,
        @Body note: CreateNoteItem
    ): Response<Notes>

    @PUT("/api/notes/update/")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Body note: UpdateNoteItem
    ): Response<NoteItem>

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

    @POST("/api/notes/deleteMany")
    suspend fun deleteManyNotes(
        @Header("Authorization") token: String,
        @Body noteIds: DeleteNoteBody
    ): Response<Notes>

    @DELETE("/api/auth/deleteProfile")
    suspend fun deleteProfile(
        @Header("Authorization") token: String
    ): Response<DeleteProfileResponse>
}

data class DeleteProfileResponse(
    val message: String
)

data class DeleteNoteBody(
    @SerializedName("ids")
    val ids: List<String>
)

data class UpdateNoteItem(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("id")
    val id: String
)