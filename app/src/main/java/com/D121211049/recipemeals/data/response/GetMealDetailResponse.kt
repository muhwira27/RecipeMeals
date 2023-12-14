package com.D121211049.recipemeals.data.response

import com.D121211049.recipemeals.data.models.DetaiedlMeal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMealDetailResponse(
    @SerialName("meals")
    val meals: List<DetaiedlMeal>
)
