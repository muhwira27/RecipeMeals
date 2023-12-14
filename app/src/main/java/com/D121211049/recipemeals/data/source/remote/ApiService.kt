package com.D121211049.recipemeals.data.source.remote

import com.D121211049.recipemeals.data.response.GetMealDetailResponse
import com.D121211049.recipemeals.data.response.GetMealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String = "Chicken"
    ): GetMealsResponse

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealDetail(
        @Query("i") idMeal: String
    ): GetMealDetailResponse
}