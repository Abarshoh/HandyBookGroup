package com.akbar.handybook.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    fun getInstance(): Retrofit {
        val retrofit: Retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://handybook.uz")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}