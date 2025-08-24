package dev.genci.test.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.genci.test.data.model.UiCocktail
import dev.genci.test.data.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: CocktailRepository
) : ViewModel() {

    private val query = MutableLiveData("")
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _lastSearch = MutableStateFlow("")
    val lastSearch: StateFlow<String> = _lastSearch

    val cocktails: LiveData<List<UiCocktail>> = query.switchMap { q ->
        _lastSearch.value = q

        if (q.isBlank()) {
            repo.cocktails
        } else {
            repo.searchLocal(q)
        }
    }

    init {
        refresh("a")

    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun refresh(query: String) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                _lastSearch.value = query

                repo.refreshByName(query)
            } catch (e: Exception) {
                // handle offline case
            }
            _isRefreshing.value = false
        }
    }
}