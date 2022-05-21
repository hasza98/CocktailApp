package hu.hasza.cocktailapp.ui.list.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.hasza.cocktailapp.R
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.Drink
import hu.hasza.cocktailapp.ui.list.all.FavCocktailListViewModel
import hu.hasza.cocktailapp.ui.list.favourites.adapter.MyFavDrinkRecyclerViewAdapter
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.android.synthetic.main.fragment_cocktail_fav_list.*
import kotlinx.android.synthetic.main.fragment_cocktail_fav_list.list
import kotlinx.android.synthetic.main.fragment_cocktail_fav_list.progressBarHolder
import kotlinx.android.synthetic.main.fragment_cocktail_list.*

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class FavCocktailListFragment : Fragment(), MyFavDrinkRecyclerViewAdapter.DrinkListClickListener {

    private val favCocktailListViewModel : FavCocktailListViewModel by viewModels()

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cocktail_fav_list, container, false)
        return view
    }

    private fun subscribeObservers() {
        favCocktailListViewModel.dataState.observe(this, Observer { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    progressBarHolder.visibility = View.VISIBLE
                }
                is DataState.Success<List<Drink>> -> {
                    progressBarHolder.visibility = View.GONE
                    if(dataState.data.isEmpty()) {
                        no_fav_cocktail_title.visibility = View.VISIBLE
                    }
                    else {
                        no_fav_cocktail_title.visibility = View.INVISIBLE
                        setupRecyclerView(dataState.data)
                    }
                }
            }
        })
    }

    private fun setupRecyclerView(drinks : List<Drink>) {
        list.layoutManager = GridLayoutManager(context, columnCount)
        list.adapter = MyFavDrinkRecyclerViewAdapter(drinks, this)
    }

    override fun onClickDrinkList(drinkId: Int) {
        val bundle = bundleOf("drinkId" to drinkId)
        findNavController(this.view!!).navigate(R.id.action_cocktail_list_tab_fragment_to_cocktail_detail_fragment, bundle)
    }
}