package com.prafull.notesapp.managers

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.component.KoinComponent

class SharedPrefManager(context: Context) : KoinComponent {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("notes_app", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun setToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun setUser(name: String, email: String) {
        sharedPreferences.edit().putString("name", name).apply()
        sharedPreferences.edit().putString("email", email).apply()
    }
}