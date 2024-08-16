package com.prafull.notesapp.main.domain.models

import com.google.gson.annotations.SerializedName

data class DeleteNote(
    @SerializedName("message")
    val message: String
)
