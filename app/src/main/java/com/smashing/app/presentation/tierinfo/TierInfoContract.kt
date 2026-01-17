package com.smashing.app.presentation.tierinfo

import androidx.compose.runtime.Immutable
import com.smashing.app.core.designsystem.style.TierInfo

interface TierInfoContract {
    @Immutable
    data class State(
        val loadState: TierInfoUiState = TierInfoUiState.Idle,
        val selectedTierInfo: TierInfo = TierInfo.IRON,

    )
}

sealed interface TierInfoUiState {
    object Idle : TierInfoUiState

    object Loading : TierInfoUiState

    object Success : TierInfoUiState

    data class Failure(
        val msg: String,
    ) : TierInfoUiState
}