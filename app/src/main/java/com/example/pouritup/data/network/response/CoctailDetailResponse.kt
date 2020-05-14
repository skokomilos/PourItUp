package com.example.pouritup.data.network.response

import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.data.network.entity.CoctailDetail
import com.google.gson.annotations.SerializedName

data class CoctailDetailResponse(
    @SerializedName("drinks")
    val drink: List <CoctailDetail>
)