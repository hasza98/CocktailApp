package hu.hasza.cocktailapp.repository


import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import hu.hasza.cocktailapp.data.local.CocktailDao
import hu.hasza.cocktailapp.data.local.CocktailDatabase
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.DetailedDrinks
import hu.hasza.cocktailapp.data.model.Drink
import hu.hasza.cocktailapp.data.model.Drinks
import hu.hasza.cocktailapp.network.CocktailApi
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository @Inject constructor(
    private val cocktailApi: CocktailApi,
    private val cocktailDao: CocktailDao
){
    private var analytics: FirebaseAnalytics = Firebase.analytics

    suspend fun getCocktails(): Flow<DataState<Drinks>> = flow{
        val bundle = Bundle()
        bundle.putString("function", "getCocktails")
        analytics.logEvent("repository_called", bundle)
        emit(DataState.Loading)
        try {
            val cocktails = cocktailApi.getCocktails()
            emit(DataState.Success(cocktails))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun searchCocktails(name : String): Flow<DataState<Drinks>> = flow{
        val bundle = Bundle()
        bundle.putString("function", "searchCocktails")
        analytics.logEvent("repository_called", bundle)
        emit(DataState.Loading)
        try {
            val cocktails = cocktailApi.searchCocktails(name)
            emit(DataState.Success(cocktails))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getCocktailDetails(cocktailId : Int): Flow<DataState<DetailedDrinks>> = flow{
        val bundle = Bundle()
        bundle.putString("function", "getCocktailDetails")
        analytics.logEvent("repository_called", bundle)
        emit(DataState.Loading)
        try {
            val cocktail = cocktailApi.getCocktailDetails(cocktailId)
            emit(DataState.Success(cocktail))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getFavourites() : Flow<DataState<List<Drink>>> = flow{
        val bundle = Bundle()
        bundle.putString("function", "getFavourites")
        analytics.logEvent("repository_called", bundle)
        emit(DataState.Loading)
        try {
            val favourites = cocktailDao.getFavourites()
            emit(DataState.Success(favourites))
        }
        catch (e: java.lang.Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun addToFavourites(cocktail : Drink) {
        val bundle = Bundle()
        bundle.putString("function", "addToFavourites")
        analytics.logEvent("repository_called", bundle)
        cocktailDao.insert(cocktail)
    }

    suspend fun removeFromFavourites(cocktail : Drink) {
        val bundle = Bundle()
        bundle.putString("function", "removeFromFavourites")
        analytics.logEvent("repository_called", bundle)
        cocktailDao.remove(cocktail)
    }

    suspend fun checkIfFavourite(id: Int) : Flow<DataState<Boolean>> = flow{
        val bundle = Bundle()
        bundle.putString("function", "checkIfFavourite")
        analytics.logEvent("repository_called", bundle)
        emit(DataState.Loading)
        try {
            val isFavourite = cocktailDao.isFavourite(id)
            emit(DataState.Success(isFavourite))
        }
        catch (e: java.lang.Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun addNewCocktail(cocktail: DetailedDrink) : Flow<DataState<Int>> = flow{
        val bundle = Bundle()
        bundle.putString("function", "addNewCocktail")
        analytics.logEvent("repository_called", bundle)
        emit(DataState.Loading)
        try {
            // API FUNCTION DOESNT EXIST REPLACED WITH MOCK RETURN
            //val id = cocktailApi.addCocktail(cocktail)
            val id = addCocktail()
            emit(DataState.Success(id))
        }
        catch (e: java.lang.Exception) {
            emit(DataState.Error(e))
        }

    }

    suspend fun updateCocktail(cocktail: DetailedDrink) : Flow<DataState<Int>> = flow{
        val bundle = Bundle()
        bundle.putString("function", "updateCocktail")
        analytics.logEvent("repository_called", bundle)
        emit(DataState.Loading)
        try {
            // API FUNCTION DOESNT EXIST REPLACED WITH MOCK RETURN
            //val id = cocktailApi.updateCocktail(cocktail)
            val id = updateCocktail()
            emit(DataState.Success(id))
        }
        catch (e: java.lang.Exception) {
            emit(DataState.Error(e))
        }

    }

    suspend fun updateCocktail() : Int {
        return 200
    }

    suspend fun addCocktail() : Int {
        return 100
    }
}