package hu.hasza.cocktailapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.hasza.cocktailapp.data.local.CocktailDao
import hu.hasza.cocktailapp.data.local.CocktailDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDrinkDb(@ApplicationContext context: Context) : CocktailDatabase {
        return Room.databaseBuilder(
            context,
            CocktailDatabase::class.java,
            CocktailDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDrinkDAO(cocktailDatabase: CocktailDatabase) : CocktailDao {
        return cocktailDatabase.CocktailDao()
    }
}