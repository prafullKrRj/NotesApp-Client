package com.prafull.notesapp.main.domain.models

import com.google.gson.annotations.SerializedName

data class CreateNoteItem(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
)