package com.smashing.app.presentation.home.regionchange

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegionChangeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RegionChangeContract.State())
    val uiState = _uiState.asStateFlow()

}