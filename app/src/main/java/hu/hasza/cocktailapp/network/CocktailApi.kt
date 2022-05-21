package hu.hasza.cocktailapp.network

import androidx.room.Update
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.DetailedDrinks
import hu.hasza.cocktailapp.data.model.Drinks
import retrofit2.Response
import retrofit2.http.*

interface CocktailApi {

    @GET("filter.php")
    suspend fun getCocktails(
        @Query("c") type: String = "Cocktail",
    ) : Drinks

    @GET("search.php")
    suspend fun searchCocktails(
        @Query("s") type: String = "",
    ) : Drinks

    @GET("lookup.php")
    suspend fun getCocktailDetails(
        @Query("i") type: Int,
    ) : DetailedDrinks

    // DOES NOT EXIST ON API
    @POST("addnew.php")
    suspend fun addCocktail(
        @Body cocktail: DetailedDrink
    ) : Int

    // DOES NOT EXIST ON API
    @PUT("update.php")
    suspend fun updateCocktail(
        @Body cocktail: DetailedDrink
    ) : Int
}