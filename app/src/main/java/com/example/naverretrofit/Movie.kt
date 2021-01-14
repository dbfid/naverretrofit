package com.example.naverretrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Movie(

    val actor: String, //배우

    val director: String, // 감독

    val image: String, // 포스터

    val link: String, // 웹 링크

    val pubDate: String, // 개봉날

    val title: String, // 제목

    val userRating: String, // 별점

    val items: List<Movie>, // 바디용 아이템

)