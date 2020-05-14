package com.example.pouritup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pouritup.adapters.CATEGORIES_PAGE_INDEX
import com.example.pouritup.adapters.INGREDIENTS_PAGE_INDEX
import com.example.pouritup.adapters.MY_FAVORITES_PAGE_INDEX
import com.example.pouritup.adapters.PourItUpPagerAdapter
import com.example.pouritup.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator


//for setup see: https://github.com/android/sunflower/blob/master/app/src/main/java/com/google/samples/apps/sunflower/HomeViewPagerFragment.kt
class HomeViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = PourItUpPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position){
            MY_FAVORITES_PAGE_INDEX -> getString(R.string.my_favorites_title)
            INGREDIENTS_PAGE_INDEX -> getString(R.string.ingredients_list_title)
            CATEGORIES_PAGE_INDEX -> getString(R.string.categories_list_title)
            else -> null
        }
    }
}