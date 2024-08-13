package com.prafull.notesapp

import com.prafull.notesapp.main.domain.models.NoteItem
import kotlinx.serialization.Serializable

sealed interface MajorRoutes {
    @Serializable
    data object HomeScreen : MajorRoutes

    @Serializable
    data object AuthScreen : MajorRoutes
}

sealed interface AuthRoutes {
    @Serializable
    data object LoginScreen : AuthRoutes

    @Serializable
    data object RegisterScreen : AuthRoutes

    @Serializable
    data object MainScreen : AuthRoutes
}

sealed interface HomeRoutes {
    @Serializable
    data object HomeScreen : HomeRoutes

    @Serializable
    data object AddNoteScreen : HomeRoutes

    @Serializable
    data class EditNoteScreen(
        val __v: Int,
        val _id: String,
        val content: String,
        val createdAt: String,
        val title: String,
        val updatedAt: String,
        val user: String
    ) : HomeRoutes {
        fun toNoteItem() = NoteItem(
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