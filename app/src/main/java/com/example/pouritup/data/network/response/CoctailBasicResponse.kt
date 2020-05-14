package com.example.pouritup.data.network.response

import com.example.pouritup.data.network.entity.CoctailBasic
import com.google.gson.annotations.SerializedName


data class CoctailBasicResponse(
    @SerializedName("drinks")
    val drinks: List<CoctailBasic>
)