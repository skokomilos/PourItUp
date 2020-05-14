package com.example.pouritup.screens.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pouritup.BaseViewModel
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.data.network.entity.CoctailDetail
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.internal.NoFieldInClass
import com.example.pouritup.internal.lazyDeferred
import com.example.pouritup.utilities.Event
import com.example.pouritup.utilities.pairs
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.reflect.Field
import kotlin.reflect.KVisibility

class DetailViewModel(private val repository: CoctailRepository, private val drinkID: String) : BaseViewModel() {

    private val _taskInsertFavorite = MutableLiveData<Event<String>>()
    val taskInsertFavorite: LiveData<Event<String>> = _taskInsertFavorite

    val isFavorite = repository.isFavorite(drinkID)

    val drinkDetail by lazyDeferred {
        repository.getDrinkById(drinkID)
    }

    val dr by lazyDeferred {
        repository.getDrinkById(drinkID).value!!.drink.get(0)
    }


    fun addToFavorites(favorite: CoctailBasic) =
        viewModelScope.launch(IO) {
            repository.addCoctailToFavorite(favorite)
            _taskInsertFavorite.postValue(Event(favorite.strDrink))
        }


    fun initialisePairs(drink: CoctailDetail): List<Pair<String, String>> {
        //ovo u viewmodel u ini ili tako nesto slicno

        val ingredientMeasurements: MutableList<Pair<String, String>> = mutableListOf()

        for (i in pairs) {

            //refleksija je morala ovde iz razloga zato sto su json podaci ponekad nepotpuni, pa se desava da objekat nije potpun pa iz tog razloga moram da proveravam polja,
            val field = drink.javaClass.getDeclaredField(i.first) as Field
            field.setAccessible(true)

            try {
                if (field.get(drink) != null && field.get(drink) != "") {
                    val fieldForMeasure = drink.javaClass.getDeclaredField(i.second) as Field
                    fieldForMeasure.setAccessible(true)
                    val pair = Pair(field.get(drink)!!.toString(), fieldForMeasure.get(drink)?.toString() ?: "-")
                    ingredientMeasurements.add(pair)
                } else {
                    break
                }
            } catch (e: NoFieldInClass) {
                Log.d("No such a field", "No Field Exception")
            }
        }

        return ingredientMeasurements
    }
}