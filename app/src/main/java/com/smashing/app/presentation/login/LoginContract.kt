package com.smashing.app.presentation.login

interface LoginContract {
    sealed interface SideEffect {
        data object NavigateToHome: SideEffect
        data class NavigateToSignUp(val kakaoId: String): SideEffect
    }
}
