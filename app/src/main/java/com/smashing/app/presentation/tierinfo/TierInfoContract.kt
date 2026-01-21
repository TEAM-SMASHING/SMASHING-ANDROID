package com.smashing.app.presentation.tierinfo

import androidx.compose.runtime.Immutable
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.data.model.rank.TierInfoDetail
import com.smashing.app.data.type.SportType

interface TierInfoContract {
    @Immutable
    data class State(
        val loadState: TierInfoUiState = TierInfoUiState.Idle,
        val sportType: SportType = SportType.PING_PONG,
        val selectedTierInfoStyle: TierInfoStyle = TierInfoStyle.IRON,
        val tierInfoDetail: TierInfoDetail? = null,
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