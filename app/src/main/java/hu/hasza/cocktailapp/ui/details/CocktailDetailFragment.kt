package hu.hasza.cocktailapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.DetailedDrinks
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.android.synthetic.main.fragment_cocktail_detail.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import hu.hasza.cocktailapp.R
import kotlinx.android.synthetic.main.fragment_cocktail_detail.progressBarHolder
import kotlinx.android.synthetic.main.fragment_cocktail_list.*
import kotlinx.android.synthetic.main.fragment_tab.*

@AndroidEntryPoint
class CocktailDetailFragment : Fragment() {
    private val DRINKID = "drinkId"
    private var drinkId: Int? = null
    private var isFavourite : Boolean = false
    private lateinit var currentDrink : DetailedDrink

    private val cocktailDetailViewModel : CocktailDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drinkId = it.getInt(DRINKID)
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_cocktail_detail_fragment_to_cocktail_list_tab_fragment)
            }
        })
        cocktailDetailViewModel.start(drinkId!!)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cocktail_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = edit_fab
        like_star.setOnClickListener {
            if(!isFavourite) {
                like_star.setImageDrawable(AppCompatResources.getDrawable(context!!, R.drawable.ic_star_24))
                isFavourite = true
                cocktailDetailViewModel.addCocktailToFavourites(currentDrink)
                Toast.makeText(context, "Added to Favourites!", Toast.LENGTH_SHORT).show()
            }
            else {
                like_star.setImageDrawable(AppCompatResources.getDrawable(context!!, R.drawable.ic_star_outline_24))
                isFavourite = false
                cocktailDetailViewModel.removeCocktailFromFavourites(currentDrink)
                Toast.makeText(context, "Removed from Favourites!", Toast.LENGTH_SHORT).show()
            }
        }
        fab.setOnClickListener {
            val bundle = bundleOf(DRINKID to drinkId)
            Navigation.findNavController(this.view!!)
                .navigate(R.id.action_cocktail_detail_fragment_to_cocktail_addorupdate_fragment, bundle)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            CocktailDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(param1, drinkId!!)
                }
            }
    }

    private fun subscribeObservers() {
        cocktailDetailViewModel.dataState.observe(this, { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    progressBarHolder.visibility = View.VISIBLE
                }
                is DataState.Success<DetailedDrinks>  -> {
                    currentDrink = dataState.data.drinks[0]
                    displayData(currentDrink)
                }
            }
        })
        cocktailDetailViewModel.exists.observe(this, { dataState ->
            when(dataState) {
                is DataState.Success<Boolean>  -> {
                    isFavourite = dataState.data
                    if(isFavourite) {
                        like_star.setImageDrawable(AppCompatResources.getDrawable(context!!, R.drawable.ic_star_24))
                    }
                    else {
                        like_star.setImageDrawable(AppCompatResources.getDrawable(context!!, R.drawable.ic_star_outline_24))
                    }
                }
            }
        })
    }

    private fun displayData(cocktail : DetailedDrink) {
        cocktail_name.text = cocktail.strDrink
        Glide.with(this)
            .load(cocktail.strDrinkThumb)
            .into(cocktailImage)
        cocktail_instructions.text = cocktail.strInstructions
        cocktail_ingridients.text = getIngridientsAndMeasures(cocktail)
        progressBarHolder.visibility = View.GONE
    }

    private fun getIngridientsAndMeasures(cocktail : DetailedDrink) : String {
        val ingridients = ArrayList<String>()
        val measures = ArrayList<String>()
        var string : String = ""
        ingridients.add(cocktail.strIngredient1)
        ingridients.add(cocktail.strIngredient2)
        ingridients.add(cocktail.strIngredient3)
        ingridients.add(cocktail.strIngredient4)
        ingridients.add(cocktail.strIngredient5)
        ingridients.add(cocktail.strIngredient6)
        ingridients.add(cocktail.strIngredient7)
        ingridients.add(cocktail.strIngredient8)
        ingridients.add(cocktail.strIngredient9)
        ingridients.add(cocktail.strIngredient10)
        ingridients.add(cocktail.strIngredient11)
        ingridients.add(cocktail.strIngredient12)
        ingridients.add(cocktail.strIngredient13)
        ingridients.add(cocktail.strIngredient14)
        ingridients.add(cocktail.strIngredient15)
        measures.add(cocktail.strMeasure1)
        measures.add(cocktail.strMeasure2)
        measures.add(cocktail.strMeasure3)
        measures.add(cocktail.strMeasure4)
        measures.add(cocktail.strMeasure5)
        measures.add(cocktail.strMeasure6)
        measures.add(cocktail.strMeasure7)
        measures.add(cocktail.strMeasure8)
        measures.add(cocktail.strMeasure9)
        measures.add(cocktail.strMeasure10)
        measures.add(cocktail.strMeasure11)
        measures.add(cocktail.strMeasure12)
        measures.add(cocktail.strMeasure13)
        measures.add(cocktail.strMeasure14)
        measures.add(cocktail.strMeasure15)
        for(ing in ingridients) {
            if(ing != null) {
                string += ing
                if(measures[ingridients.indexOf(ing)] != null) {
                    string += " " + measures[ingridients.indexOf(ing)] + "\n"
                }
                else {
                    string += "\n"
                }
            }
        }
        return string
    }

}