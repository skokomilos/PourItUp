package com.example.pouritup.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.internal.lazyDeferred

class FavoriteViewModel(
    private val repository: CoctailRepository
): ViewModel()  {
    val favorites by lazyDeferred {
        repository.getFavorites()
    }
}