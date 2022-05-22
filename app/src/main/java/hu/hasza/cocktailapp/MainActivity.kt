package hu.hasza.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import hu.hasza.cocktailapp.data.model.Drinks
import hu.hasza.cocktailapp.ui.list.all.CocktailListViewModel
import hu.hasza.cocktailapp.ui.list.all.FavCocktailListViewModel
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        analytics = Firebase.analytics
        val bundle = Bundle()
        bundle.putString("title", "cocktailApp")
        analytics.logEvent("app_started", bundle)
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash")
        }
    }
}