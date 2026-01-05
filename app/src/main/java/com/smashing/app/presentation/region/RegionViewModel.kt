package com.smashing.app.presentation.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.common.state.UiState
import com.smashing.app.data.model.Region
import com.smashing.app.data.repository.api.KakaoRegionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegionViewModel @Inject constructor(
    private val regionRepository: KakaoRegionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegionContract.State())
    val uiState = _uiState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = query)
        }
    }

    fun fetchRegion(query: String) = viewModelScope.launch {
        if (query.isBlank()) return@launch

        _uiState.update { currentState ->
            currentState.copy(regionLoadState = UiState.Loading)
        }

        regionRepository.searchAddress(query)
            .onSuccess { regions ->
                _uiState.update { currentState ->
                    currentState.copy(
                        regionLoadState = if (regions.isNotEmpty()) {
                            UiState.Success(regions.toImmutableList())
                        } else {
                            UiState.Failure("검색 결과가 없습니다")
                        },
                    )
                }
            }
            .onFailure { throwable ->

                Timber.tag("RegionViewModel").e(
                    throwable,
                    "주소 검색 실패: query=$query, error=${throwable.message}"
                )
                _uiState.update { currentState ->
                    currentState.copy(
                        regionLoadState = UiState.Failure(
                            throwable.message ?: "주소 검색에 실패했습니다",
                        ),
                    )
                }
            }
    }

    fun getRegion(region: Region) {
        _uiState.update { currentState ->
            Timber.d(region.region2depthName)
            currentState.copy(selectedRegion = region)
        }
    }
}