package com.example.pouritup.screens.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pouritup.data.network.response.CategoryResponse
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.internal.lazyDeferred

class CategoryViewModel(private val repository: CoctailRepository): ViewModel() {

    val categories by lazyDeferred {
        repository.getCategories()
    }
}