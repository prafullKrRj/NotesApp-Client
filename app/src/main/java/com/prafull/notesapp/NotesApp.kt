package com.prafull.notesapp

import android.app.Application
import com.prafull.notesapp.auth.data.AuthApiService
import com.prafull.notesapp.auth.ui.AuthViewModel
import com.prafull.notesapp.main.data.ApiService
import com.prafull.notesapp.main.data.NotesRepositoryImpl
import com.prafull.notesapp.main.domain.repos.NotesRepository
import com.prafull.notesapp.main.ui.screens.HomeViewModel
import com.prafull.notesapp.managers.SharedPrefManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesApp)
            modules(
                module {
                    viewModel<AuthViewModel> { AuthViewModel(get(), get()) }
                    single<AuthApiService> {
                        Retrofit.Builder()
                            .baseUrl("http://192.168.194.184:5000/")
                            .addConverterFactory(
                                GsonConverterFactory.create()
                            )
                            .build()
                            .create(AuthApiService::class.java)
                    }
                    single<NotesRepository> {
                        NotesRepositoryImpl(get())
                    }
                    single<SharedPrefManager> {
                        SharedPrefManager(get())
                    }
                    single<ApiService> {
                        Retrofit.Builder().baseUrl("http://192.168.194.184:5000/")
                            .addConverterFactory(GsonConverterFactory.create()).build()
                            .create(ApiService::class.java)
                    }
                    viewModel { HomeViewModel(get(), get()) }
                }
            )
        }
    }
}