package com.prafull.notesapp.main.ui.screens.profilescreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.notesapp.auth.domain.User
import com.prafull.notesapp.main.data.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileViewModel(
    context: Context
) : ViewModel(), KoinComponent {
    private val apiService by inject<ApiService>()
    private val prefs = context.getSharedPreferences("notes_pref", 0)
    val user by mutableStateOf(
        User(
            name = prefs.getString("name", "")!!,
            email = prefs.getString("email", "")!!,
            password = prefs.getString("password", "")!!
        )
    )
    private val token = prefs.getString("token", "")!!
    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()
    fun deleteAccount() {
        _loading.update { true }
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.deleteProfile(token)
            if (response.isSuccessful) {
                Log.d("ProfileViewModel", "deleteAccount: ${response.body()}")
            }
            clearPrefs()
        }
    }

    fun clearPrefs() {
        prefs.edit().clear().apply()
    }
}
