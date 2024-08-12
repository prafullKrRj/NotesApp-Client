package com.prafull.notesapp.auth.domain

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    val token: String
)