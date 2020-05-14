package com.example.pouritup.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pouritup.data.network.response.CategoryResponse
import com.example.pouritup.data.network.response.CoctailBasicResponse
import com.example.pouritup.data.network.response.CoctailDetailResponse
import com.example.pouritup.data.network.response.IngredientResponse
import com.example.pouritup.internal.NoConnectivityException

class CoctailNetworkDataSourceImpl(
    private val coctailApiService: CoctailApiService
) : CoctailNetworkDataSource {

    private val _downloadedCoctailDetails = MutableLiveData<CoctailBasicResponse>()
    override val downloadedCoctailDetails: LiveData<CoctailBasicResponse>
        get() = _downloadedCoctailDetails

    private val _downloadedIngredientsCoctail  = MutableLiveData<IngredientResponse>()
    override val downloadedIngredientsCoctail: LiveData<IngredientResponse>
        get() = _downloadedIngredientsCoctail

    private val _downloadedCategoriesCoctail = MutableLiveData<CategoryResponse>()
    override val downloadedCategoriesCoctail: LiveData<CategoryResponse>
        get() = _downloadedCategoriesCoctail

    private val _downloadedDrink = MutableLiveData<CoctailDetailResponse>()
    override val downloadedDrink: LiveData<CoctailDetailResponse>
        get() = _downloadedDrink


    override suspend fun fetchCategories() {
        try {
            val fetchedCoctailCategories = coctailApiService
                .getAllCagegories()
            _downloadedCategoriesCoctail.postValue(fetchedCoctailCategories)
        }catch (e: NoConnectivityException){
            Log.d("Connectivity", "No internet connection")
        }
    }

    override suspend fun fetchIngredients() {
        try {
            val fetchIngredients = coctailApiService
                .getIngridients()
            _downloadedIngredientsCoctail.postValue(fetchIngredients)
        }catch (e : NoConnectivityException){
            Log.d("Connectivity", "No internet connection")
        }
    }

    override suspend fun fetchDrinksByCategoryName(categoryName: String) {
        try {
           val fetchDrinks = coctailApiService.getBasicCoctailByCategoryName(categoryName)
            _downloadedCoctailDetails.postValue(fetchDrinks)
       }catch (e : NoConnectivityException){
           Log.d("Connectivity", "No internet connection")
       }
    }

    override suspend fun fetchDrinksByIngredientName(ingredientName: String){
        try{
            val fetchDrinks = coctailApiService.getBasicCoctailByIngridientName(ingredientName)
            _downloadedCoctailDetails.postValue(fetchDrinks)
        }catch (e : NoConnectivityException){
            Log.d("Connectivity", "No internet connection")
        }
    }


    override suspend fun fetchDrinkByID(drinkID: String){
        try {
            val drink = coctailApiService.getDrinkByID(drinkID)
            _downloadedDrink.postValue(drink)
        }catch (e: NoConnectivityException){
            Log.d("Connectivity", "No internet connection")
        }
    }
}