package com.example.pouritup.screens.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pouritup.HomeViewFragmentDirections
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.Category
import com.example.pouritup.databinding.FragmentCategoriesBinding
import com.example.pouritup.screens.CustomViewModelFactory
import com.example.pouritup.screens.base.ScopedFragment
import com.example.pouritup.screens.detail.CustomViewModelFactoryWithArgs
import com.xwray.groupie.GroupAdapter
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_categories.*
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance

class CategoriesFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private lateinit var viewModel: CategoryViewModel
    private val factory : CustomViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(CategoryViewModel::class.java)

        bindUI()
    }


    override fun bindUI() = launch{
        viewModel.categories.await().observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            group_loading_categories.visibility = View.GONE

            initRecyclerView(it.category.toCategoryItem())
        })
    }

    private fun List<Category>.toCategoryItem() : List<CategoryItem>{
        return this.map {
            CategoryItem(it)
        }
    }

    private fun initRecyclerView(items : List<CategoryItem>){
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(this@CategoriesFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener{ item, view->
            if(item is CategoryItem){
                Navigation.findNavController(view).navigate(
                    HomeViewFragmentDirections
                        .actionDestinationHomeToDrinksFragment(item.category.strCategory, "")
                )
            }
        }
    }
}