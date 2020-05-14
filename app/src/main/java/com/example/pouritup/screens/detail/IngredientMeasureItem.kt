package com.example.pouritup.screens.detail

import android.location.GnssMeasurement
import android.util.Log
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.CoctailDetail
import com.example.pouritup.data.network.entity.Ingredient
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_ingredient.*
import kotlinx.android.synthetic.main.item_ingredient_measurement.*

class IngredientMeasureItem(val pair: Pair<String, String>): Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
       viewHolder.apply {
           val (ingredient, measurement) = pair
               mes_ing_tv.text = ingredient.trim()
               ing_mes_tv.text = measurement.trim()
       }
    }

    override fun getLayout() = R.layout.item_ingredient_measurement

}