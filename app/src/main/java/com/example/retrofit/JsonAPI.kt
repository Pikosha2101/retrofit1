package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface JsonAPI {

    @GET("all")
    fun getPosts() : Call<List<DataModel>>
}