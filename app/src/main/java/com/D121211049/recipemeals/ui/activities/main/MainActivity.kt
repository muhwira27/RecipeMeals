package com.D121211049.recipemeals.ui.activities.main


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.D121211049.recipemeals.data.models.Meal
import com.D121211049.recipemeals.ui.activities.detail.DetailActivity
import com.D121211049.recipemeals.ui.theme.RecipeMealsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeMealsTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        CenterAlignedTopAppBar(
                            modifier = Modifier.background(Color.Blue),
                            title = {
                                Text(
                                    text = "Recipe Meals",
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        )
                        val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.Factory)
                        ListMealsScreen(mainViewModel, mainViewModel.mainUiState)
                    }
                }
            }
        }
    }

    @Composable
    private fun ListMealsScreen(mainViewModel: MainViewModel, mainUiState: MainUiState, modifier: Modifier = Modifier) {
        when (mainUiState) {
            is MainUiState.Loading -> LoadingIndicator()
            is MainUiState.Error -> ErrorScreen("Gagal memuat data. Silakan coba lagi.")
            is MainUiState.Success -> MealList(mainViewModel, mainUiState.meals)
        }
    }

    @Composable
    fun MealList(mainViewModel: MainViewModel, meals: List<Meal>, modifier: Modifier = Modifier) {
        LazyColumn(modifier = modifier) {
            item {
                CategoryDropdown(mainViewModel)
            }
            items(meals) { meal ->
                MealItem(meal = meal)
            }
        }
    }

    @Composable
    fun MealItem(meal: Meal) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable {
                    Log.d("idMeal", "$meal.idMeal")
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("MEAL_ID", meal.idMeal)
                    startActivity(intent)
                }
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Meal Image
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(meal.strMealThumb)
                        .crossfade(true)
                        .build(), contentDescription = meal.strMeal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                // Meal Name
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = meal.strMeal ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun LoadingIndicator() {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }

    @Composable
    fun ErrorScreen(errorMsg: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = "Error icon",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }

    @Composable
    fun CategoryDropdown(mainViewModel: MainViewModel) {
        val categories = listOf("Chicken", "Beef", "Dessert", "Pasta", "Seafood", "Vegetarian", "Breakfast")
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Filter by Category",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = mainViewModel.selectedCategory,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 2.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            mainViewModel.onCategorySelected(category)
                            expanded = false
                        },
                        text = {
                            Text(
                                text = category,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}
