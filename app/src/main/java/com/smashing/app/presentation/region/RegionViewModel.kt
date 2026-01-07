package com.smashing.app.presentation.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.common.state.UiState
import com.smashing.app.data.model.Region
import com.smashing.app.data.repository.api.RegionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class RegionViewModel @Inject constructor(
    private val regionRepository: RegionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegionContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState
                .map { it.searchQuery }
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .debounce(timeoutMillis = SEARCH_DELAY)
                .collect { query ->
                    fetchRegion(query)
                }
        }
    }

    fun updateSearchQuery(query: String) = _uiState.update { currentState ->
        currentState.copy(searchQuery = query)
    }

    private fun updateRegionLoadState(state: UiState<ImmutableList<Region>>) =
        _uiState.update { currentState ->
            currentState.copy(regionLoadState = state)
        }


    fun fetchRegion(query: String) = viewModelScope.launch {
        if (query.isBlank()) return@launch

        updateRegionLoadState(UiState.Loading)

        regionRepository.searchAddress(query)
            .onSuccess { regions ->
                updateRegionLoadState(
                    if (regions.isNotEmpty()) {
                        UiState.Success(regions.toImmutableList())
                    } else {
                        UiState.Failure("검색 결과가 없습니다")
                    },
                )
            }
            .onFailure { throwable ->
                updateRegionLoadState(
                    UiState.Failure(
                        throwable.message ?: "주소 검색에 실패했습니다",
                    ),
                )
            }
    }

    fun updateSelectedRegion(region: Region) = _uiState.update { currentState ->
        currentState.copy(selectedRegion = region)
    }

    companion object {
        private const val SEARCH_DELAY = 500L
    }
}