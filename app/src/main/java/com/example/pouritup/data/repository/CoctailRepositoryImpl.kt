package com.example.pouritup.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pouritup.data.db.dao.BasicCoctailDao
import com.example.pouritup.data.network.CoctailNetworkDataSource
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.data.network.entity.CoctailDetail
import com.example.pouritup.data.network.response.CategoryResponse
import com.example.pouritup.data.network.response.CoctailBasicResponse
import com.example.pouritup.data.network.response.CoctailDetailResponse
import com.example.pouritup.data.network.response.IngredientResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class CoctailRepositoryImpl(
    private val coctailNetworkDataSource: CoctailNetworkDataSource,
    private val coctailBasicDao: BasicCoctailDao
) : CoctailRepository {

    init {
        coctailNetworkDataSource.downloadedCategoriesCoctail.observeForever {

        }
    }

    override suspend fun getCategories(): LiveData<CategoryResponse> {
        fetchcateg()
        return coctailNetworkDataSource.downloadedCategoriesCoctail
    }

    override suspend fun fetchcateg() {
        coctailNetworkDataSource.fetchCategories()
    }

    override suspend fun fetchIngred() {
        coctailNetworkDataSource.fetchIngredients()
    }


    override suspend fun getIngredients() : LiveData<IngredientResponse> {
        fetchIngred()
        return coctailNetworkDataSource.downloadedIngredientsCoctail
    }

    override suspend fun getDrinks(categoryName: String, ingredientName: String): LiveData<CoctailBasicResponse> {
        when{
            categoryName != "" -> fetchDrinksByCategoryName(categoryName)
            ingredientName != "" -> fetchdrinkByIngredientname(ingredientName)
        }
        return coctailNetworkDataSource.downloadedCoctailDetails
    }

    override suspend fun fetchDrinksByCategoryName(categoryName : String) {
        coctailNetworkDataSource.fetchDrinksByCategoryName(categoryName)
    }

    override suspend fun fetchdrinkByIngredientname(ingredientName: String){
        coctailNetworkDataSource.fetchDrinksByIngredientName(ingredientName)
    }

    override suspend fun fetchdrink(id: String) {
        coctailNetworkDataSource.fetchDrinkByID(id)
    }

    override suspend fun getDrinkById(id: String): LiveData<CoctailDetailResponse>{
        fetchdrink(id)
        return coctailNetworkDataSource.downloadedDrink
    }

    override suspend fun getFavorites(): LiveData<List<CoctailBasic>> {
        return withContext(IO){
            coctailBasicDao.getFavorites()
        }
    }

    override suspend fun addCoctailToFavorite(favorite: CoctailBasic) {
        coctailBasicDao.insertDrink(favorite)
    }

    override fun isFavorite(drinkId: String): LiveData<Boolean>{
        return coctailBasicDao.isFavorite(drinkId)
    }

    override fun getFavorite(drinkId: String) =  coctailBasicDao.getFavorite(drinkId)
}