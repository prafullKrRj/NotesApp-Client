package com.prafull.notesapp.main.domain.models

import com.google.gson.annotations.SerializedName
import com.prafull.notesapp.HomeRoutes

data class NoteItem(
    @SerializedName("__v")
    val __v: Int,
    @SerializedName("_id")
    val _id: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user")
    val user: String
) {
    fun toEditableNote(): HomeRoutes.EditNoteScreen {
        return HomeRoutes.EditNoteScreen(
            __v = __v,
            _id = _id,
            content = content,
            createdAt = createdAt,
            title = title,
            updatedAt = updatedAt,
            user = user
        )
    }
}

data class CreateNoteModel(
    val title: String,
    val content: String
)