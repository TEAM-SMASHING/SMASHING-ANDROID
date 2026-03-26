package com.smashing.app.presentation.withdraw

import androidx.compose.runtime.Immutable

interface WithdrawContract {
    @Immutable
    data class State(
        val isWithdrawalAgreed: Boolean = false,
        val withdrawUiState: WithdrawUiState = WithdrawUiState.Idle,
    )

    sealed interface WithdrawSideEffect {
        data object NavigateToLogin : WithdrawSideEffect
    }
}

sealed interface WithdrawUiState {
    data object Idle : WithdrawUiState

    data object Loading : WithdrawUiState

    data object Success : WithdrawUiState

    data class Failure(
        val msg: String,
    ) : WithdrawUiState
}
