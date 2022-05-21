package hu.hasza.cocktailapp.ui.addorupdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AddOrUpdateCocktailViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()

    private val _dataState : MutableLiveData<DataState<DetailedDrinks>> = MutableLiveData()

    val dataState : LiveData<DataState<DetailedDrinks>>
        get() = _dataState

    private val _dataStateInt : MutableLiveData<DataState<Int>> = MutableLiveData()

    val dataStateInt : LiveData<DataState<Int>>
        get() = _dataStateInt

    fun addOrUpdate(cocktail: DetailedDrink) {
        if(cocktail.idDrink==-1) {
            viewModelScope.launch {
                cocktailRepository.addNewCocktail(cocktail).onEach {
                        dataState -> _dataStateInt.value = dataState }.launchIn(viewModelScope)
            }
        }
        else {
            viewModelScope.launch {
                cocktailRepository.updateCocktail(cocktail).onEach {
                        dataState -> _dataStateInt.value = dataState }.launchIn(viewModelScope)
            }
        }
    }

    fun start(id: Int) {
        _id.value = id
        viewModelScope.launch {
            cocktailRepository.getCocktailDetails(_id.value!!).onEach {
                    dataState -> _dataState.value = dataState }.launchIn(viewModelScope)
        }
    }
}