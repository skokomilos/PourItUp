package com.example.pouritup.screens.category

import android.view.View
import androidx.navigation.findNavController
import com.example.pouritup.HomeViewFragmentDirections
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.Category
import com.example.pouritup.data.network.response.CategoryResponse
import com.example.pouritup.databinding.ItemCategoriesBinding
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_categories.*

class CategoryItem(
    val category : Category
): BindableItem<ItemCategoriesBinding>() {


    //groupie = viewholder free
    override fun bind(viewBinding: ItemCategoriesBinding, position: Int) {

        category.javaClass.declaredFields.forEach {
            println("${it.name} or type ${it.type}")
        }

        viewBinding.category = category
        viewBinding.textViewCategories.text = category.strCategory
    }

    private fun navigateToDrinks(
        category: Category,
        it: View
    ){
        val direction =
            HomeViewFragmentDirections.actionDestinationHomeToDrinksFragment(category.strCategory, "")
        it.findNavController().navigate(direction)
    }

    override fun getLayout() = R.layout.item_categories
}