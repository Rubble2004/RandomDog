package com.example.a1801.models.API

import com.example.a1801.models.Dog
import retrofit2.http.GET

const val base_url = "https://random.dog/"

    interface ApiRequest{
        @GET("woof.json")
        suspend fun getRandomDog(): Dog

    }