package com.D121211049.recipemeals.ui.activities.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.D121211049.recipemeals.data.models.DetaiedlMeal
import com.D121211049.recipemeals.ui.theme.RecipeMealsTheme


class DetailActivity : ComponentActivity() {
    private  var mealId: String = ""

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DetailActivity", "Received meal ID: $mealId")
        mealId = intent.getStringExtra("MEAL_ID") ?: ""
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
                                    text = "Detail Recipe Meal",
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                }
                            },
                        )
                        val viewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)
                        Log.d("DetailActivity", "Calling getMealDetail with ID: $mealId")
                        viewModel.getMealDetail(mealId)
                        DetailScreen(viewModel)
                    }

                }
            }
        }
    }

//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun DetailTopBar() {
//        TopAppBar(
//            modifier = Modifier.background(Color.Blue),
//            title = {
//                Text(
//                    text = "Detail Recipe Meal",
//                    fontWeight = FontWeight.SemiBold
//                )
//            },
//            navigationIcon = {
//                IconButton(onClick = { onBackPressed() }) {
//                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                }
//            },
//        )
//    }

    @Composable
    fun DetailScreen(viewModel: DetailViewModel) {
        val detailUiState by viewModel.detailUiState.observeAsState()
        when (detailUiState) {
            is DetailUiState.Loading -> LoadingIndicator()
            is DetailUiState.Error -> ErrorScreen("Gagal memuat data. Silakan coba lagi.")
            is DetailUiState.Success -> MealDetail((detailUiState as DetailUiState.Success).meals[0])
            else -> {ErrorScreen("Gagal memuat data. Silakan coba lagi.")}
        }
    }

    @Composable
    fun MealDetail(meal: DetaiedlMeal) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(meal.strMealThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = meal.strMeal ?: "No title",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            meal.strCategory?.let { category ->
                Text(
                    text = "Category: $category",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            meal.strArea?.let { area ->
                Text(
                    text = "Area: $area",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Instructions:", fontWeight = FontWeight.Bold)

            meal.strInstructions?.let { instructions ->
                Text(
                    text = instructions.replace("\n", "\n\n"),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp) )
            Text(
                "Ingredients:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
                )
            meal.ingredients().forEach { ingredient ->
                Text(text = "- ${ingredient.first}: ${ingredient.second}")
            }
        }
    }

    private fun DetaiedlMeal.ingredients(): List<Pair<String?, String?>> {
        return listOf(
            Pair(strIngredient1, strMeasure1),
            Pair(strIngredient2, strMeasure2),
            Pair(strIngredient3, strMeasure3),
            Pair(strIngredient4, strMeasure4),
            Pair(strIngredient5, strMeasure5),
            Pair(strIngredient6, strMeasure6),
            Pair(strIngredient7, strMeasure7),
            Pair(strIngredient8, strMeasure8),
            Pair(strIngredient9, strMeasure9),
            Pair(strIngredient10, strMeasure10),
            Pair(strIngredient11, strMeasure11),
            Pair(strIngredient12, strMeasure12),
            Pair(strIngredient13, strMeasure13),
            Pair(strIngredient14, strMeasure14),
            Pair(strIngredient15, strMeasure15),
            Pair(strIngredient16, strMeasure16),
            Pair(strIngredient17, strMeasure17),
            Pair(strIngredient18, strMeasure18),
            Pair(strIngredient19, strMeasure19),
            Pair(strIngredient20, strMeasure20),
        ).filterNot { it.first.isNullOrEmpty() }
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
}
