package hu.hasza.cocktailapp.ui.list.all

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.Drink
import hu.hasza.cocktailapp.data.model.Drinks
import hu.hasza.cocktailapp.repository.CocktailRepository
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavCocktailListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _dataState : MutableLiveData<DataState<List<Drink>>> = MutableLiveData()

    val dataState : LiveData<DataState<List<Drink>>>
        get() = _dataState

    init {
        viewModelScope.launch { cocktailRepository.getFavourites().onEach {
                dataState -> _dataState.value = dataState }.launchIn(viewModelScope)
        }
    }
}