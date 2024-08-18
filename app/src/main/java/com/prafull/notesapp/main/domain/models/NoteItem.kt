package com.prafull.notesapp.main.domain.models

import com.google.gson.annotations.SerializedName
import com.prafull.notesapp.HomeRoutes

data class NoteItem(
    @SerializedName("__v")
    val __v: Int,
    @SerializedName("_id")
    val _id: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user")
    val user: String
) {
    fun toEditNoteRoute() =
        HomeRoutes.EditNoteScreen(__v, _id, content, createdAt, title, updatedAt, user)
}