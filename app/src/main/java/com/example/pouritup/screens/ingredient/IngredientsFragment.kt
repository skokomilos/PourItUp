package com.example.pouritup.screens.ingredient

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pouritup.HomeViewFragmentDirections
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.Ingredient
import com.example.pouritup.screens.CustomViewModelFactory
import com.example.pouritup.screens.base.ScopedFragment
import com.example.pouritup.screens.category.CategoryItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_ingredients.*
import kotlinx.android.synthetic.main.item_igredient.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

const val INSET_TYPE_KEY = "inset_type"
const val INSET = "inset"
class IngredientsFragment : ScopedFragment(), KodeinAware {


    override val kodein: Kodein by closestKodein()
    private lateinit var viewModel: IngredientViewModel
    private val factory : CustomViewModelFactory by instance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this,factory)
            .get(IngredientViewModel::class.java)

        bindUI()
    }


    override fun bindUI() = launch {
        viewModel.ingredients.await().observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            group_loading_ingredients.visibility = View.GONE

            initRecyclerView(it.ingridient.toIngredientItem())
        })
    }
    //todo animacija ne radi
    private val handler = Handler()
    private fun List<Ingredient>.toIngredientItem() : List<IngredientItem>{

        return this.map {
            IngredientItem(it) { item, favorite ->
                // Pretend to make a network request
                handler.postDelayed({
                    // Network request was successful!
                    item.setFavorite(favorite)
                    item.notifyChanged(FAVORITE)
                }, 1000)
            }
        }
    }

    private fun initRecyclerView(items : List<IngredientItem>){
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        recyclerViewIngredients.apply {
            layoutManager = LinearLayoutManager(this@IngredientsFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener{ item, view ->
            if(item is IngredientItem) {
                Navigation.findNavController(view).navigate(
                    HomeViewFragmentDirections
                        .actionDestinationHomeToDrinksFragment("", item.ingredient.strIngredient1)
                )
            }
        }
    }
}