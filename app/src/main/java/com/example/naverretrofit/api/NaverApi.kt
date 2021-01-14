package com.example.naverretrofit.api

import com.example.naverretrofit.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {

    @GET("v1/search/movie.json")
    fun getSearchMovie(@Query("query") query: String): Call<Movie>

}