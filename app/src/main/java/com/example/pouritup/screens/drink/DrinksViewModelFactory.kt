package com.example.pouritup.screens.drink

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pouritup.data.repository.CoctailRepository


class DrinksViewModelFactory(
    private val array : Array<String>,
    private val repostitory: CoctailRepository): ViewModelProvider.NewInstanceFactory() {

    val categoryName = array[0]
    val ingredientName = array[1]


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkViewModel::class.java)) {
            return DrinkViewModel(
                repostitory,
                categoryName,
                ingredientName
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}