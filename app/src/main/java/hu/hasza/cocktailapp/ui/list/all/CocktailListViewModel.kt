package hu.hasza.cocktailapp.ui.list.all

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hasza.cocktailapp.data.model.Drinks
import hu.hasza.cocktailapp.repository.CocktailRepository
import hu.hasza.cocktailapp.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _dataState : MutableLiveData<DataState<Drinks>> = MutableLiveData()

    val dataState : LiveData<DataState<Drinks>>
        get() = _dataState

    private val _searchdataState : MutableLiveData<DataState<Drinks>> = MutableLiveData()

    val searchdataState : LiveData<DataState<Drinks>>
        get() = _searchdataState

    fun start() {
        viewModelScope.launch {
            cocktailRepository.getCocktails().onEach { dataState -> _dataState.value = dataState }
                .launchIn(viewModelScope)
        }
    }

    fun search(name: String) {
        viewModelScope.launch { cocktailRepository.searchCocktails(name).onEach {
                dataState -> _searchdataState.value = dataState }.launchIn(viewModelScope)
        }
    }
}