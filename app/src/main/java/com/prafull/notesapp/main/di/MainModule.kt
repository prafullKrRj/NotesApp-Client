package com.prafull.notesapp.main.di

import com.prafull.notesapp.main.data.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
    single<ApiService> {
        Retrofit.Builder().baseUrl("http://192.168.194.184:5000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }
}