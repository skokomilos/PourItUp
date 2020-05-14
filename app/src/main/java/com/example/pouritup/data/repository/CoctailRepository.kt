package com.example.pouritup.data.repository

import androidx.lifecycle.LiveData
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.data.network.entity.CoctailDetail
import com.example.pouritup.data.network.response.CategoryResponse
import com.example.pouritup.data.network.response.CoctailBasicResponse
import com.example.pouritup.data.network.response.CoctailDetailResponse
import com.example.pouritup.data.network.response.IngredientResponse

interface CoctailRepository {

    suspend fun getCategories(): LiveData<CategoryResponse>
    suspend fun fetchcateg()
    suspend fun fetchIngred()
    suspend fun getIngredients(): LiveData<IngredientResponse>
    suspend fun getDrinks(categoryName: String, ingredientName: String): LiveData<CoctailBasicResponse>
    //ovo posmatraj kao fetch data from network
    suspend fun getDrinkById(id: String): LiveData<CoctailDetailResponse>
    suspend fun fetchdrink(id: String)
    suspend fun fetchDrinksByCategoryName(categoryName: String)
    suspend fun fetchdrinkByIngredientname(ingredientName: String)
    suspend fun getFavorites(): LiveData<List<CoctailBasic>>
    suspend fun addCoctailToFavorite(favorite: CoctailBasic)
    fun isFavorite(drinkId: String): LiveData<Boolean>
    fun getFavorite(drinkId: String): LiveData<CoctailBasic>
}