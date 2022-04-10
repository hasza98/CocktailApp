package hu.hasza.cocktailapp.repository

import hu.hasza.cocktailapp.data.local.CocktailDao
import hu.hasza.cocktailapp.data.remote.CocktailApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository @Inject constructor(
    private val cocktailApi: CocktailApi,
    private val cocktailDao: CocktailDao
){
}