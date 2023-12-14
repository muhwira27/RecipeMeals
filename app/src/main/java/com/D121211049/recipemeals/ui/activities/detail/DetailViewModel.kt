package com.D121211049.recipemeals.ui.activities.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.D121211049.recipemeals.MyApplication
import com.D121211049.recipemeals.data.models.DetaiedlMeal
import com.D121211049.recipemeals.data.repository.MealsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface DetailUiState {
    data class Success(val meals: List<DetaiedlMeal>) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(private val mealsRepository: MealsRepository) : ViewModel() {
    val detailUiState: MutableLiveData<DetailUiState> = MutableLiveData(DetailUiState.Loading)

    fun getMealDetail(idMeal: String) = viewModelScope.launch {
        detailUiState.postValue(DetailUiState.Loading)
        try {
            val response = mealsRepository.getMealDetail(idMeal)
            Log.d("DetailViewModel", "Response received: $response")
            if (response.meals.isNotEmpty()) {
                detailUiState.postValue(DetailUiState.Success(response.meals))
            } else {
                // Handle empty list atau tampilkan error
                detailUiState.postValue(DetailUiState.Error)
            }
        } catch (e: IOException) {
            Log.e("DetailViewModel", "Error fetching meal detail", e)
            detailUiState.postValue(DetailUiState.Error)
        }
    }


    // Factory pattern untuk membuat ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val mealsRepository = application.container.mealsRepository
                DetailViewModel(mealsRepository)
            }
        }
    }
}