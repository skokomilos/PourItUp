package com.example.pouritup.screens.drink

import androidx.lifecycle.ViewModel
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.internal.lazyDeferred

class DrinkViewModel(private val repository: CoctailRepository, private val categoryName: String, private val ingredientName: String) : ViewModel() {

    val drinks by lazyDeferred {
        repository.getDrinks(categoryName, ingredientName)
    }
}