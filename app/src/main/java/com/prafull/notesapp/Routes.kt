package com.prafull.notesapp

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
}