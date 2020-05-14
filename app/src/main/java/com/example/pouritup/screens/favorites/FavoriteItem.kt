package com.example.pouritup.screens.favorites

import com.example.pouritup.R
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.internal.glide.GlideApp
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.list_item_drink.*

class FavoriteItem(
    val drink : CoctailBasic
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            drink_item_title.text = drink.strDrink
            updateImage()
        }
    }

    override fun getLayout() = R.layout.item_favorite

    private fun GroupieViewHolder.updateImage(){
        GlideApp.with(this.containerView)
            .load(drink.strDrinkThumb)
            .into(drink_item_image)
    }
}