package com.example.pouritup.screens.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.screens.drink.DrinkViewModel


@Suppress("UNCHECKED_CAST")
class CustomViewModelFactoryWithArgs(
    val drinkId: String,
    val repository: CoctailRepository
) : ViewModelProvider.NewInstanceFactory() {

    private val mApplication: Application? = null


    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when{
                isAssignableFrom(DetailViewModel::class.java) ->
                    DetailViewModel(
                        repository,
                        drinkId
                    )else -> throw IllegalAccessException("Unknown ViewModel class: ${modelClass.name}")
            }
    } as T
}