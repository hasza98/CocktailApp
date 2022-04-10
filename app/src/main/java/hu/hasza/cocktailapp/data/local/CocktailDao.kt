package hu.hasza.cocktailapp.data.local

import androidx.room.Dao

@Dao
interface CocktailDao {

    fun getCocktails();

    fun insert();

    fun delete();

    fun deleteAll();
}