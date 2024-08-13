package com.prafull.notesapp.auth.domain

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("__v")
    val __v: Int,
    @SerializedName("_id")
    val _id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("password")
    val password: String
) {
    fun toUser() = User(
        email = email, password = password, name = name
    )
}