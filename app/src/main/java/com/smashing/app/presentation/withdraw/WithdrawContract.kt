package com.smashing.app.presentation.withdraw

import androidx.compose.runtime.Immutable

interface WithdrawContract {
    @Immutable
    data class State(
        val isWithdrawalAgreed: Boolean = false,
        val withdrawUiState: WithdrawUiState = WithdrawUiState.Idle,
    )
}

sealed interface WithdrawUiState {
    object Idle : WithdrawUiState

    object Success : WithdrawUiState

    data class Failure(
        val msg: String,
    ) : WithdrawUiState
}
