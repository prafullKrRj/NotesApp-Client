package com.prafull.notesapp.auth.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.notesapp.auth.data.AuthApiService
import com.prafull.notesapp.auth.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AuthViewModel(
    private val apiService: AuthApiService,
    context: Context
) : ViewModel(), KoinComponent {
    private val prefs = context.getSharedPreferences("notes_pref", Context.MODE_PRIVATE)
    var hasLoggedIn by mutableStateOf(false)
    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("AuthViewModel", "Logging in user ${"$email $password"}")
            val response = apiService.login(
                User(email, password)
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    hasLoggedIn = true
                    prefs.edit().putString("token", it.token).apply()
                    prefs.edit().putBoolean("isLoggedIn", true).apply()
                }
            } else {
                Log.e("AuthViewModel", "Error: ${response.errorBody().toString()}")
            }
        }
    }

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.register(
                User(email, password, name)
            )
            if (response.isSuccessful) {
                Log.d("AuthViewModel", "User registered successfully $response")
                login(email, password)
            } else {
                Log.e("AuthViewModel", "Error: ${response.errorBody()}")
            }
        }
    }
}