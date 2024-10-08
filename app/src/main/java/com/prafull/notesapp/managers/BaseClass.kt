package com.prafull.notesapp.managers

sealed class BaseClass<out T> {
    data class Success<out T>(val data: T) : BaseClass<T>()
    data class Error(val exception: String) : BaseClass<Nothing>()
    data object Loading : BaseClass<Nothing>()
}