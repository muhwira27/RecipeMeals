package com.D121211049.recipemeals.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Meal(
    val strMeal: String?,
    val strMealThumb: String?,
    val idMeal: String?
): Parcelable
