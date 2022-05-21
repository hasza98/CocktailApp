package hu.hasza.cocktailapp.ui.list.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.hasza.cocktailapp.R
import hu.hasza.cocktailapp.data.model.Drink
import hu.hasza.cocktailapp.data.model.Drinks
import hu.hasza.cocktailapp.ui.list.all.adapter.MyDrinkRecyclerViewAdapter
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.android.synthetic.main.cocktail_list_item.*
import kotlinx.android.synthetic.main.fragment_cocktail_list.*

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class CocktailListFragment : Fragment(), MyDrinkRecyclerViewAdapter.DrinkListClickListener {

    private val cocktailListViewModel : CocktailListViewModel by viewModels()

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cocktailListViewModel.start()
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cocktail_list, container, false)
        return view
    }

    private fun subscribeObservers() {
        cocktailListViewModel.dataState.observe(this, Observer { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    progressBarHolder.visibility = View.VISIBLE
                }
                is DataState.Success<Drinks> -> {
                    setupRecyclerView(dataState.data.drinks)
                    progressBarHolder.visibility = View.GONE
                }
            }
        })
        cocktailListViewModel.searchdataState.observe(this, Observer { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    progressBarHolder.visibility = View.VISIBLE
                }
                is DataState.Success<Drinks> -> {
                    progressBarHolder.visibility = View.GONE
                    if(dataState.data.drinks != null) {
                        no_cocktail_title.visibility = View.GONE
                        setupRecyclerView(dataState.data.drinks)
                    }
                    else {
                        setupRecyclerView(emptyList())
                        no_cocktail_title.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) {
                    if(text.isNotEmpty()) {
                        cocktailListViewModel.search(text)
                    }
                    else {
                        cocktailListViewModel.start()
                    }
                }
                else {
                    cocktailListViewModel.start()
                }
                return false
            }
        })
    }

    private fun setupRecyclerView(drinks : List<Drink>) {
        list.layoutManager = GridLayoutManager(context, columnCount)
        list.adapter = MyDrinkRecyclerViewAdapter(drinks, this)
    }

    override fun onClickDrinkList(drinkId: Int) {
        val bundle = bundleOf("drinkId" to drinkId)
        findNavController(this.view!!).navigate(R.id.action_cocktail_list_tab_fragment_to_cocktail_detail_fragment, bundle)
    }
}