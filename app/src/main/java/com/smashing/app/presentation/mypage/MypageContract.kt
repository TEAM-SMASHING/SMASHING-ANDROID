package com.smashing.app.presentation.mypage

import androidx.compose.runtime.Immutable
import com.smashing.app.presentation.mypage.model.MyPageProfileUiModel


interface MyPageContract {
    @Immutable
    data class State(
        val profileLoadState: MyPageUiState = MyPageUiState.Idle,
        val myPageProfileInfo: MyPageProfileUiModel = MyPageProfileUiModel(),
        val selectedSportProfileId: String = "",
    )

    sealed interface MyPageUiState {
        data object Idle : MyPageUiState
        data object Loading : MyPageUiState
        data object Success : MyPageUiState
        data class Failure(
            val msg: String,
        ) : MyPageUiState
    }
}
