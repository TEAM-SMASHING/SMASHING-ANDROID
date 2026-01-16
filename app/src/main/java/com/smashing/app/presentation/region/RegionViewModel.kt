package com.smashing.app.presentation.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.common.state.UiState
import com.smashing.app.domain.model.Region
import com.smashing.app.domain.usecase.GetSeoulFilterRegionUseCase
import com.smashing.app.presentation.home.regionchange.RegionChangeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class RegionViewModel @Inject constructor(
    private val getSeoulFilterRegionUseCase: GetSeoulFilterRegionUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegionContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegionContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

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

        getSeoulFilterRegionUseCase(query)
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

    fun updateSelectedRegion(region: Region) {
        _uiState.update { currentState ->
            currentState.copy(selectedRegion = region)
        }.also {
            viewModelScope.launch {
                _sideEffect.emit(
                    RegionContract.SideEffect.NavigateToRegionChange(
                        addressName = region.addressName,
                        cityName = region.cityName,
                        districtName = region.districtName,
                    )
                )
            }
        }
    }

    fun updateNavigateUp() = viewModelScope.launch {
        _sideEffect.emit(RegionContract.SideEffect.NavigateUp)
    }

    companion object {
        private const val SEARCH_DELAY = 500L
    }
}