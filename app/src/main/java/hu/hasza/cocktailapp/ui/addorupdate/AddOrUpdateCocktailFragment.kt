package hu.hasza.cocktailapp.ui.addorupdate

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import hu.hasza.cocktailapp.R
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.DetailedDrinks
import hu.hasza.cocktailapp.ui.details.CocktailDetailViewModel
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.android.synthetic.main.fragment_add_or_update_cocktail.*
import kotlinx.android.synthetic.main.fragment_cocktail_detail.*
import android.content.Intent
import android.net.Uri
import android.widget.Toast

import androidx.core.app.ActivityCompat.startActivityForResult




@AndroidEntryPoint
class AddOrUpdateCocktailFragment : Fragment() {
    private val DRINKID = "drinkId"
    private var drinkId: Int? = null
    private lateinit var currentDrink : DetailedDrink
    private var imageUrl : String = ""
    var SELECT_PICTURE = 200

    private val addOrUpdateCocktailViewModel : AddOrUpdateCocktailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drinkId = it.getInt(DRINKID)
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(drinkId == null) {
                    findNavController().navigate(R.id.action_cocktail_addorupdate_fragment_to_cocktail_list_tab_fragment)
                }
                else {
                    val bundle = bundleOf(DRINKID to drinkId)
                    findNavController().navigate(R.id.action_cocktail_addorupdate_fragment_to_cocktail_detail_fragment, bundle)
                }
            }
        })
        subscribeObservers()
        if(drinkId != null) {
            addOrUpdateCocktailViewModel.start(drinkId!!)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_or_update_cocktail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(drinkId != null) {
            addOrUpdateButton.text = "UPDATE"
        }
        else {
            addOrUpdateButton.text = "CREATE"
        }
        addImageButton.setOnClickListener {
            openGalleryForImage()
        }
        addOrUpdateButton.setOnClickListener {
            if(drinkId!= null) {
                val toUpdate = currentDrink
                toUpdate.strDrinkThumb = imageUrl
                toUpdate.strDrink = titleEditText.text.toString()
                toUpdate.strInstructions = preparationEditText.text.toString()
                toUpdate.strIngredient1 = ingridient1.text.toString()
                toUpdate.strIngredient2 = ingridient2.text.toString()
                toUpdate.strIngredient3 = ingridient3.text.toString()
                toUpdate.strIngredient4 = ingridient4.text.toString()
                toUpdate.strMeasure1 = amount1.text.toString()
                toUpdate.strMeasure2 = amount2.text.toString()
                toUpdate.strMeasure3 = amount3.text.toString()
                toUpdate.strMeasure4 = amount4.text.toString()
                addOrUpdateCocktailViewModel.addOrUpdate(toUpdate)
            }
            else {
                val toCreate = DetailedDrink()
                toCreate.idDrink = -1
                toCreate.strDrinkThumb = imageUrl
                toCreate.strDrink = titleEditText.text.toString()
                toCreate.strInstructions = preparationEditText.text.toString()
                toCreate.strIngredient1 = ingridient1.text.toString()
                toCreate.strIngredient2 = ingridient2.text.toString()
                toCreate.strIngredient3 = ingridient3.text.toString()
                toCreate.strIngredient4 = ingridient4.text.toString()
                toCreate.strMeasure1 = amount1.text.toString()
                toCreate.strMeasure2 = amount2.text.toString()
                toCreate.strMeasure3 = amount3.text.toString()
                toCreate.strMeasure4 = amount4.text.toString()
                addOrUpdateCocktailViewModel.addOrUpdate(toCreate)
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE){
            imageUrl = data?.data.toString()
            Glide.with(this)
                .load(data?.data)
                .into(addUpdateImage)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            AddOrUpdateCocktailFragment().apply {
                arguments = Bundle().apply {
                    putInt(param1, drinkId!!)
                }
            }
    }

    private fun subscribeObservers() {
        if(drinkId!=null) {
            addOrUpdateCocktailViewModel.dataState.observe(this, { dataState ->
                when(dataState) {
                    is DataState.Success<DetailedDrinks>  -> {
                        currentDrink = dataState.data.drinks[0]
                        imageUrl = currentDrink.strDrinkThumb
                        updateUI(currentDrink)
                    }
                }
            })
        }
        addOrUpdateCocktailViewModel.dataStateInt.observe(this, { dataState ->
            when(dataState) {
                is DataState.Success<Int>  -> {
                    Toast.makeText(this.context, dataState.data.toString(), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_cocktail_addorupdate_fragment_to_cocktail_list_tab_fragment)
                }
            }
        })
    }

    private fun updateUI(currentDrink: DetailedDrink) {
        Glide.with(this)
            .load(currentDrink.strDrinkThumb)
            .into(addUpdateImage)
        titleEditText.setText(currentDrink.strDrink)
        preparationEditText.setText(currentDrink.strInstructions)
        ingridient1.setText(currentDrink.strIngredient1)
        amount1.setText(currentDrink.strMeasure1)
        ingridient2.setText(currentDrink.strIngredient2)
        amount1.setText(currentDrink.strMeasure2)
        ingridient3.setText(currentDrink.strIngredient3)
        amount1.setText(currentDrink.strMeasure3)
        ingridient4.setText(currentDrink.strIngredient4)
        amount1.setText(currentDrink.strMeasure4)
    }
}