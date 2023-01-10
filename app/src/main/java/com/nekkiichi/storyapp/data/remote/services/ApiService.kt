package com.nekkiichi.storyapp.data.remote.services

import com.nekkiichi.storyapp.data.remote.response.BasicResponse
import com.nekkiichi.storyapp.data.remote.response.FullAuthResponse
import com.nekkiichi.storyapp.data.remote.response.ListStoryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.File

interface ApiService {
    @POST("/register")
    suspend fun sendRegister(
        @Query("name")
        name:String,
        @Query("email")
        email: String,
        @Query("password")
        password: String
    ):BasicResponse

    @POST("/login")
    suspend fun sendLogin(
        @Query("email")
        email: String,
        @Query("password")
        password: String
    ): FullAuthResponse

    @POST("/stories")
    suspend fun sendStory(
        @Header("Authorization") tokenWithBeaver: String,
        @Query("description") description: String,
        @Query("photo") photo: File,
    ): BasicResponse

    @GET("/stories")
    suspend fun getAllStories(
        @Header("Authorization") tokenWithBeaver: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ListStoryResponse
}