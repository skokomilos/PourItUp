package com.example.pouritup.screens.drink

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pouritup.R
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.screens.CustomViewModelFactory
import com.example.pouritup.screens.base.ScopedFragment
import com.example.pouritup.screens.detail.CustomViewModelFactoryWithArgs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_drinks.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance

class DrinksFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private lateinit var viewModel: DrinkViewModel
    private val factory : ((mParams: Array<String>) -> DrinksViewModelFactory) by factory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let{ DrinksFragmentArgs.fromBundle(it)}
        val categoryName = safeArgs?.categoryName
        val ingredientName = safeArgs?.ingredientName

        //todo ovo resi prvo dozvoljavas nullable pa onda ne


        viewModel = ViewModelProvider(this, factory(arrayOf(categoryName!!, ingredientName!!)))
                .get(DrinkViewModel::class.java)


        if(categoryName != "") {
            drinks_toolbar_text_view.text = categoryName
        }else if(ingredientName != ""){
            drinks_toolbar_text_view.text = ingredientName
        }

        bindUI()
    }

    override fun bindUI() = launch {
        viewModel.drinks.await().observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            initRecyclerView(it.drinks.toDrinkItem())
        })
    }

    private fun List<CoctailBasic>.toDrinkItem() : List<DrinkItem>{
        return this.map{
            DrinkItem(it)
        }
    }

    private fun initRecyclerView(items: List<DrinkItem>) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        drink_list.apply {
            layoutManager = GridLayoutManager(this@DrinksFragment.context, groupAdapter.spanCount).apply {
                spanCount = 2
            }
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener{ item, view ->
            if(item is DrinkItem)
                Navigation.findNavController(view).navigate(
                    DrinksFragmentDirections
                        .actionDrinksFragmentToDetailFragment(item.drink.idDrink))
        }
    }
}