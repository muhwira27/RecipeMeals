package com.D121211049.recipemeals.data.repository

import com.D121211049.recipemeals.data.response.GetMealDetailResponse
import com.D121211049.recipemeals.data.response.GetMealsResponse
import com.D121211049.recipemeals.data.source.remote.ApiService

class MealsRepository(private val apiService: ApiService) {
    suspend fun getMealsByCategory(category: String): GetMealsResponse {
        return apiService.getMealsByCategory(category)
    }

    suspend fun getMealDetail(idMeal: String): GetMealDetailResponse {
        return apiService.getMealDetail(idMeal)
    }
}