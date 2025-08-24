package dev.genci.test.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.genci.test.data.model.UiCocktail
import dev.genci.test.data.repository.CocktailRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: CocktailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle["cocktailId"])

    val cocktail: LiveData<UiCocktail?> = repo.cocktailById(id)

    init {
        viewModelScope.launch { repo.refreshDetail(id) }
    }
}