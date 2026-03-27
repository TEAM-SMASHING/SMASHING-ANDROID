package com.smashing.app.presentation.mypage

import androidx.compose.runtime.Immutable
import com.smashing.app.presentation.mypage.model.MyPageProfileUiModel


interface MyPageContract {
    @Immutable
    data class State(
        val profileLoadState: MyPageUiState = MyPageUiState.Idle,
        val myPageProfileInfo: MyPageProfileUiModel = MyPageProfileUiModel(),
        val selectedSportProfileId: String = "",
        val logoutLoadState: MyPageUiState = MyPageUiState.Idle,
    )

    sealed interface MyPageUiState {
        data object Idle : MyPageUiState
        data object Loading : MyPageUiState
        data object Success : MyPageUiState
        data class Failure(
            val msg: String,
        ) : MyPageUiState
        sealed interface MyPageSideEffect {
            data object NavigateToLogin : MyPageSideEffect
        }
    }
}
