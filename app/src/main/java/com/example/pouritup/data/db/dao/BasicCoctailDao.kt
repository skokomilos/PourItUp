package com.example.pouritup.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pouritup.data.network.entity.CoctailBasic

@Dao
interface BasicCoctailDao {


    @Query("SELECT EXISTS(SELECT 1 FROM coctail_basic WHERE idDrink = :drinkId LIMIT 1)")
    fun isFavorite(drinkId: String): LiveData<Boolean>

    @Query("SELECT * FROM coctail_basic")
    fun getFavorites(): LiveData<List<CoctailBasic>>

    @Query("SELECT * FROM coctail_basic WHERE idDrink = :drinkId")
    fun getFavorite(drinkId: String): LiveData<CoctailBasic>

    @Insert
    fun insertDrink(drink : CoctailBasic)

    @Query("SELECT * FROM coctail_basic")
    fun getAllDrinks(): List<CoctailBasic>

    @Query("SELECT * FROM coctail_basic WHERE idDrink = :id LIMIT 1")
    suspend fun getDrinkById(id: String): CoctailBasic


}