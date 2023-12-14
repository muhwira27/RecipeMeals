package com.D121211049.recipemeals.ui.activities.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.D121211049.recipemeals.MyApplication
import com.D121211049.recipemeals.data.models.Meal
import com.D121211049.recipemeals.data.repository.MealsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MainUiState {
    data class Success(val meals: List<Meal>) : MainUiState
    object Error : MainUiState
    object Loading : MainUiState
}

class MainViewModel(private val mealsRepository: MealsRepository): ViewModel() {
    var selectedCategory: String by mutableStateOf("Chicken")

    // Initial state
    var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)
        private set

    fun getMealsByCategory(category: String = selectedCategory) = viewModelScope.launch {
        mainUiState = MainUiState.Loading
        try {
            val response = mealsRepository.getMealsByCategory(category)
            mainUiState = MainUiState.Success(response.meals)
        } catch (e: IOException) {
            mainUiState = MainUiState.Error
        }
    }

    fun onCategorySelected(category: String) {
        selectedCategory = category
        getMealsByCategory(category)
    }

    // Method to initialize ViewModel
    init {
        getMealsByCategory()
    }

    // Factory pattern untuk membuat ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val mealsRepository = application.container.mealsRepository
                MainViewModel(mealsRepository)
            }
        }
    }
}

