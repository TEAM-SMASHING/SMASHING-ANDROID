package com.smashing.app.presentation.home.regionchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.RegionRepository
import com.smashing.app.domain.model.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionChangeViewModel @Inject constructor(
    private val regionRepository: RegionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegionChangeContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegionChangeContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()


    fun updateLoadState(state: RegionChangeUiState) {
        _uiState.update { currentState ->
            currentState.copy(
                regionLoadState = state,
            )
        }
    }

    fun updateSelectedRegion(region: Region) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRegion = region,
                regionLoadState = RegionChangeUiState.Success,
            )
        }
    }

    fun updateToRegion() = viewModelScope.launch {
        _sideEffect.emit(RegionChangeContract.SideEffect.NavigateToRegion)
    }

    fun changeRegion() = viewModelScope.launch {
        val region = _uiState.value.selectedRegion ?: return@launch

        updateLoadState(RegionChangeUiState.Loading)

        regionRepository.changeRegion(region.districtName)
            .onSuccess {
                updateLoadState(RegionChangeUiState.Success)
                _sideEffect.emit(RegionChangeContract.SideEffect.RegionChangeSuccess)
            }
            .onFailure { throwable ->
                updateLoadState(RegionChangeUiState.Failure(throwable.message ?: "지역 변경에 실패했습니다."))
            }
    }
}
