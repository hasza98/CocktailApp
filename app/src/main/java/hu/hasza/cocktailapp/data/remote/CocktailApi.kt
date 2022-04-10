package hu.hasza.cocktailapp.data.remote

interface CocktailApi {

    fun getCocktails();

    fun searchCoctails();

    fun getCocktailDetails();
}