package hu.hasza.cocktailapp.ui.details

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.DetailedDrinks
import hu.hasza.cocktailapp.data.model.Drink
import hu.hasza.cocktailapp.repository.CocktailRepository
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _dataState : MutableLiveData<DataState<DetailedDrinks>> = MutableLiveData()

    val dataState : LiveData<DataState<DetailedDrinks>>
        get() = _dataState

    private val _exists : MutableLiveData<DataState<Boolean>> = MutableLiveData()

    val exists : LiveData<DataState<Boolean>>
        get() = _exists

    fun start(id: Int) {
        _id.value = id
        viewModelScope.launch {
            cocktailRepository.getCocktailDetails(_id.value!!).onEach {
                    dataState -> _dataState.value = dataState }.launchIn(viewModelScope)
            cocktailRepository.checkIfFavourite(id).onEach {
                    dataState -> _exists.value = dataState }.launchIn(viewModelScope)
        }
    }

    fun addCocktailToFavourites(cocktail : DetailedDrink) =
        viewModelScope.launch { cocktailRepository.addToFavourites(Drink(cocktail)) }

    fun removeCocktailFromFavourites(cocktail : DetailedDrink) =
        viewModelScope.launch { cocktailRepository.removeFromFavourites(Drink(cocktail)) }

}