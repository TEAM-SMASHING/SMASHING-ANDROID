package com.smashing.app.presentation.home.regionchange

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.smashing.app.presentation.home.navigation.RegionChange
import com.smashing.app.presentation.region.navigation.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegionChangeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegionChangeContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegionChangeContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getRegion() {
        Timber.tag("RegionChangeViewModel").d("dddd")
        Timber.tag("RegionChangeViewModel").d(savedStateHandle.toRoute<RegionChange>().addressName)
        Timber.tag("RegionChangeViewModel").d(savedStateHandle.toRoute<RegionChange>().districtName)
        Timber.tag("RegionChangeViewModel").d(savedStateHandle.toRoute<RegionChange>().cityName)
    }
}