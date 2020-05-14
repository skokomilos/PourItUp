package com.example.pouritup.data.network.response


import com.example.pouritup.data.network.entity.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("drinks")
    val category: List<Category>
)