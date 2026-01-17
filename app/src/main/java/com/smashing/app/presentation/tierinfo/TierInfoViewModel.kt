package com.smashing.app.presentation.tierinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.smashing.app.core.designsystem.style.TierInfo
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

    fun updateTierInfo(tierInfo: TierInfo) {
        _uiState.update {
            it.copy(
                selectedTierInfo = tierInfo,
            )
        }
    }

    private fun updateInitialTierInfo(savedStateHandle: SavedStateHandle) {
        val tierInfoString = savedStateHandle.get<String>("tierInfo")
            ?: TierInfo.IRON.name

        val tierInfo = try {
            TierInfo.valueOf(tierInfoString)
        } catch (e: IllegalArgumentException) {
            TierInfo.IRON
        }

        _uiState.update {
            it.copy(
                selectedTierInfo = tierInfo,
            )
        }
    }

}