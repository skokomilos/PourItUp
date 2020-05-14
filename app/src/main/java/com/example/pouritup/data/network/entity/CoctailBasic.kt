package com.example.pouritup.data.network.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coctail_basic")
data class CoctailBasic(
    @PrimaryKey(autoGenerate = false)
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
)