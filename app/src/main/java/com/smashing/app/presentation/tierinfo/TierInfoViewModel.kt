package com.smashing.app.presentation.tierinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.core.designsystem.style.toTierInfoStyle
import com.smashing.app.presentation.tierinfo.navigation.TierInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TierInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TierInfoContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        updateInitialTierInfo(savedStateHandle)
    }

    fun updateTierInfo(tierInfoStyle: TierInfoStyle) {
        _uiState.update {
            it.copy(
                selectedTierInfoStyle = tierInfoStyle,
            )
        }
    }

    private fun updateInitialTierInfo(savedStateHandle: SavedStateHandle) {
        val tierInfo = savedStateHandle.toRoute<TierInfo>().tierInfo.toTierInfoStyle()

        updateTierInfo(tierInfo)
    }
}