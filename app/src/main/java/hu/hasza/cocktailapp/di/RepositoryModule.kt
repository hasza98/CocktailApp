package hu.hasza.cocktailapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.hasza.cocktailapp.data.local.CocktailDao
import hu.hasza.cocktailapp.network.CocktailApi
import hu.hasza.cocktailapp.repository.CocktailRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCocktailRepository(cocktailApi: CocktailApi, cocktailDao : CocktailDao) : CocktailRepository {
        return CocktailRepository(cocktailApi = cocktailApi, cocktailDao = cocktailDao)
    }
}