package hu.hasza.cocktailapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.Drink

@Database(entities = [Drink::class], version = 2)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun CocktailDao(): CocktailDao

    companion object {
        val DATABASE_NAME: String = "drinks_db"
    }
}