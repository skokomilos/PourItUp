package com.example.pouritup.screens.detail

import android.icu.lang.UCharacter
import android.os.Bundle
import android.telecom.Call
import android.telecom.Connection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.data.network.entity.CoctailDetail
import com.example.pouritup.data.network.response.CoctailDetailResponse
import com.example.pouritup.databinding.FragmentDetailBinding
import com.example.pouritup.internal.DrinkIDNotFound
import com.example.pouritup.internal.glide.GlideApp
import com.example.pouritup.internal.lazyDeferred
import com.example.pouritup.screens.CustomViewModelFactory
import com.example.pouritup.screens.base.ScopedFragment
import com.example.pouritup.utilities.EventObserver
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.w3c.dom.ProcessingInstruction

class DetailFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private lateinit var detailviewModel: DetailViewModel
    private val viewModelFactoryInstanceFactory : ((String) -> CustomViewModelFactoryWithArgs) by factory()

    interface Callback{
        fun add(favorite: Deferred<CoctailDetail>)
    }

    //i tok this solution from google's sunflower app, (fab button visibility)
    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(arguments != null) {
            val safeArgs = arguments?.let { DetailFragmentArgs.fromBundle(it) }
            val id = safeArgs?.drinkID ?: throw DrinkIDNotFound()

            detailviewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(id))
                .get(DetailViewModel::class.java)
        }

        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater, R.layout.fragment_detail, container, false
        ).apply {
            this.viewModel = detailviewModel
            this.setLifecycleOwner(this@DetailFragment)
            this.callback = object : Callback {
                override fun add(favorite: Deferred<CoctailDetail>) {
                    favorite.let {
                        hideAppBarFab(fab)
                        CoroutineScope(IO).launch {
                            val fav = favorite.await()
                            detailviewModel.addToFavorites(CoctailBasic(
                                fav.idDrink,
                                fav.strDrink,
                                fav.strDrinkThumb
                            ))
                        }
                            detailviewModel.taskInsertFavorite.observe(viewLifecycleOwner, EventObserver{favDrink ->
                                Snackbar.make(root, "drink $favDrink added to favorites", Snackbar.LENGTH_LONG)
                                    .show()
                            })
                    }
                }
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindUI()
    }

    lateinit var drink: CoctailDetail
    override fun bindUI() = launch{
        drink = detailviewModel.drinkDetail.await().value!!.drink.get(0)
        println(drink.strCategory)

        val pairs = detailviewModel.initialisePairs(drink)

        initRecyclerView(pairs.toIngredientMeasurementsItem())

        updateName(drink.strDrink)
        updateInstructions(drink.strInstructions)
        updateCategory(drink.strCategory)

        GlideApp.with(this@DetailFragment)
            .load(drink.strDrinkThumb)
            .into(detail_image)
    }

    private fun updateName(name: String?){
        drink_detail_name.text = name ?: "-"
    }

    private fun updateInstructions(instruction: String?){
            drink_instructions.text = instruction ?: "-"
    }

    private fun updateCategory(category: String?){
        drink_category.text = category ?: "-"
    }

    private fun List<Pair<String, String>>.toIngredientMeasurementsItem(): List<IngredientMeasureItem>{
        return this.map {
            IngredientMeasureItem(it)
        }
    }

    private fun initRecyclerView(items: List<IngredientMeasureItem>){
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        recyclerView_drink_igredients_measurements.isNestedScrollingEnabled = false
        recyclerView_drink_igredients_measurements.apply {
            layoutManager = LinearLayoutManager(this@DetailFragment.context)
            adapter = groupAdapter
        }
    }
}