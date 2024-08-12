package com.prafull.notesapp.auth.ui

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
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {

    private val apiService: AuthApiService by inject()

    var hasLoggedIn by mutableStateOf(false)
    fun login(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.login(
                User(email, password, name)
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    hasLoggedIn = true
                }
            } else {
                Log.e("AuthViewModel", "Error: ${response.errorBody()}")
            }
        }
    }
}