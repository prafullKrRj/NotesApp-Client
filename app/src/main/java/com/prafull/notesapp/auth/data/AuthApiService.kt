package com.prafull.notesapp.auth.data

import com.prafull.notesapp.auth.domain.LoginResponse
import com.prafull.notesapp.auth.domain.User
import com.prafull.notesapp.auth.domain.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/api/auth/login")
    suspend fun login(
        @Body user: User
    ): Response<LoginResponse>

    @POST("/api/auth/register")
    suspend fun register(
        @Body user: User
    ): Response<UserResponse>

}