package com.example.pouritup.data.network.response

import com.example.pouritup.data.network.entity.Ingredient
import com.google.gson.annotations.SerializedName

data class IngredientResponse(
    @SerializedName("drinks")
    val ingridient: List<Ingredient>
)