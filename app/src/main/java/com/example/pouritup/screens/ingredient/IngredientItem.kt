package com.example.pouritup.screens.ingredient

import android.graphics.Color
import android.graphics.drawable.Animatable
import android.util.Log
import androidx.annotation.ColorInt
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.Ingredient
import com.example.pouritup.databinding.ItemIgredientBinding
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_igredient.*
import kotlinx.android.synthetic.main.item_ingredient.*
import kotlinx.android.synthetic.main.item_ingredient.textViewIngredient

val FAVORITE = "FAVORITE"

class IngredientItem(
    val ingredient: Ingredient,
    private val onFavoriteListener : (item : IngredientItem, favorite: Boolean) -> Unit
) : BindableItem<ItemIgredientBinding>(){


    private var checked = false
    private var inProgress = false

    init {
        extras.put(INSET_TYPE_KEY, INSET)
    }

    override fun getLayout() = R.layout.item_igredient

    private fun bindHeart(holder: GroupieViewHolder) {
        if (inProgress) {
            animateProgress(holder)
        } else {
            holder.favorite.setImageResource(R.drawable.favorite_state_list)
        }
        holder.favorite.isChecked = checked
    }

    private fun animateProgress(holder: GroupieViewHolder) {
        holder.favorite.apply {
            setImageResource(R.drawable.avd_favorite_progress)
            (drawable as Animatable).start()
        }
    }

    fun setFavorite(favorite: Boolean) {
        inProgress = false
        checked = favorite
    }

    override fun bind(viewBinding: ItemIgredientBinding, position: Int) {
        viewBinding.ingredient = ingredient
        viewBinding.textViewIngredient.text = ingredient.strIngredient1
    }

}