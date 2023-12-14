package com.D121211049.recipemeals.data.response

import com.D121211049.recipemeals.data.models.Meal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMealsResponse(
    @SerialName("meals")
    val meals: List<Meal>
)
