package com.example.pouritup.screens.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pouritup.HomeViewFragmentDirections
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.databinding.FragmentFavoritesBinding
import com.example.pouritup.screens.CustomViewModelFactory
import com.example.pouritup.screens.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.item_igredient.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FavoritesFragments : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private lateinit var viewModel: FavoriteViewModel
    private val factory: CustomViewModelFactory by instance()
    lateinit var binding : FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
        return binding.root
    }

    override fun bindUI() = launch(Dispatchers.Main){
        viewModel.favorites.await().observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            group_loading_favorites.visibility = View.GONE
            initRecyclerView(it.toFavoriteItem())
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }


    private fun List<CoctailBasic>.toFavoriteItem() : List<FavoriteItem>{
        return this.map {
            FavoriteItem(it)
        }
    }

    private fun initRecyclerView(items: List<FavoriteItem>) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        recyclerViewFavorites.apply {
            layoutManager =
                GridLayoutManager(this@FavoritesFragments.context, groupAdapter.spanCount).apply {
                    spanCount = 2
                }
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            if (item is FavoriteItem) {
                Navigation.findNavController(view).navigate(
                    HomeViewFragmentDirections.actionDestinationHomeToDetailFragment(item.drink.idDrink)
                )
            }

        }

    }
}