package com.smashing.app.presentation.login

interface LoginContract {
    sealed interface SideEffect {
        data object NavigateToHome: SideEffect
        data class NavigateToSignUp(val kakaoId: String): SideEffect
    }
}

sealed interface LoginUiState {
    object Idle : LoginUiState

    object Loading : LoginUiState

    object Success : LoginUiState

    data class Failure(
        val msg: String,
    ) : LoginUiState
}
