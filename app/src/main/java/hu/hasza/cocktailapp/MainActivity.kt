package hu.hasza.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import hu.hasza.cocktailapp.data.model.Drinks
import hu.hasza.cocktailapp.ui.list.all.CocktailListViewModel
import hu.hasza.cocktailapp.ui.list.all.FavCocktailListViewModel
import hu.hasza.cocktailapp.utils.DataState
import java.lang.StringBuilder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}