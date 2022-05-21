package hu.hasza.cocktailapp.data.local

import androidx.room.*
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.Drink

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktail: Drink) : Long

    @Query("SELECT * FROM drinks")
    suspend fun getFavourites() : List<Drink>

    @Delete
    suspend fun remove(cocktail: Drink)

    @Query("SELECT EXISTS(SELECT * FROM drinks WHERE idDrink = :id)")
    suspend fun isFavourite(id : Int) : Boolean
}