package com.example.pouritup.screens

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.screens.category.CategoryViewModel
import com.example.pouritup.screens.drink.DrinkViewModel
import com.example.pouritup.screens.favorites.FavoriteViewModel
import com.example.pouritup.screens.ingredient.IngredientViewModel
import java.util.*


//viewmodes are used for perserving state, during rotation our view is destroyed but as we said viewmodel is state persever
//because fragments will be destroyed but our viewmodel not, so we cannot make new instance of viewmodel every single time when rotation is happend
//so basicly we want to create only once viewmodel instance druing first creation or launching of our fragment
//preservation of our viewmodels is ViewModelProvider job
//factories just know how to create new instances of objects in this case of viewmodel

@Suppress("UNCHECKED_CAST")
class CustomViewModelFactory constructor(
    private val repository: CoctailRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass){

            when{
                isAssignableFrom(FavoriteViewModel::class.java) ->
                    FavoriteViewModel(
                        repository
                    )
                isAssignableFrom(IngredientViewModel::class.java) ->
                    IngredientViewModel(
                        repository
                    )
                isAssignableFrom(CategoryViewModel::class.java) ->
                    CategoryViewModel(
                        repository
                    )
                else ->
                    throw IllegalAccessException("Unknown ViewModel class: ${modelClass.name}")
            }
    } as T
}