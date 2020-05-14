package com.example.pouritup.data.network

import androidx.lifecycle.LiveData
import com.example.pouritup.data.network.entity.CoctailDetail
import com.example.pouritup.data.network.response.CategoryResponse
import com.example.pouritup.data.network.response.CoctailBasicResponse
import com.example.pouritup.data.network.response.CoctailDetailResponse
import com.example.pouritup.data.network.response.IngredientResponse

interface CoctailNetworkDataSource {
    val downloadedCoctailDetails: LiveData<CoctailBasicResponse>
    val downloadedIngredientsCoctail: LiveData<IngredientResponse>
    val downloadedCategoriesCoctail: LiveData<CategoryResponse>
    val downloadedDrink: LiveData<CoctailDetailResponse>

    suspend fun fetchCategories()
    suspend fun fetchIngredients()
    suspend fun fetchDrinksByIngredientName(ingredientName: String)
    suspend fun fetchDrinkByID(id: String)
    suspend fun fetchDrinksByCategoryName(categoryName: String)
}