package com.D121211049.recipemeals.data

import com.D121211049.recipemeals.data.repository.MealsRepository
import com.D121211049.recipemeals.data.source.remote.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val mealsRepository: MealsRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.themealdb.com"

    val json = Json {
        ignoreUnknownKeys = true // Add this line to ignore unknown JSON keys
        // You might have other configuration options here
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val mealsRepository: MealsRepository
        get() = MealsRepository(retrofitService)
}
