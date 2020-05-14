package com.example.pouritup.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pouritup.screens.category.CategoriesFragment
import com.example.pouritup.screens.ingredient.IngredientsFragment
import com.example.pouritup.screens.favorites.FavoritesFragments
import java.lang.IndexOutOfBoundsException

const val MY_FAVORITES_PAGE_INDEX = 0
const val INGREDIENTS_PAGE_INDEX = 1
const val CATEGORIES_PAGE_INDEX = 2

class PourItUpPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val tabFragmentCreator: Map<Int, () -> Fragment> = mapOf(
            MY_FAVORITES_PAGE_INDEX to { FavoritesFragments() },
            INGREDIENTS_PAGE_INDEX to { IngredientsFragment() },
            CATEGORIES_PAGE_INDEX to { CategoriesFragment() }
    )

    override fun getItemCount() = tabFragmentCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}