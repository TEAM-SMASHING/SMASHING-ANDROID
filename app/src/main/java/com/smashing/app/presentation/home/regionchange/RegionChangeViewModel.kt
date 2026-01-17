package com.smashing.app.presentation.home.regionchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class RegionChangeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RegionChangeContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegionChangeContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

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

}
