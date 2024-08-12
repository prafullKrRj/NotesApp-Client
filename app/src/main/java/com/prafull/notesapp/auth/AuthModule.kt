package com.prafull.notesapp.auth

import com.prafull.notesapp.auth.data.AuthApiService
import com.prafull.notesapp.auth.ui.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val authModule = module {
    viewModel { AuthViewModel() }
    single<AuthApiService> {
        Retrofit.Builder()
            .baseUrl("http://192.168.194.184:5000/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(AuthApiService::class.java)
    }
}