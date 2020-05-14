package com.example.pouritup.screens.ingredient

import androidx.lifecycle.ViewModel
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.internal.lazyDeferred

class IngredientViewModel(private val repository: CoctailRepository): ViewModel()  {

    val ingredients by lazyDeferred {
        repository.getIngredients()
    }
}