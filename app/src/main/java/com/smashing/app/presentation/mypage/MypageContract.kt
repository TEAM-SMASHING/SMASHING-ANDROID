package com.smashing.app.presentation.mypage

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.my.MyProfileInfo

interface MyPageContract {
    @Immutable
    data class State(
        val profileLoadState: MyPageUiState = MyPageUiState.Idle,
        val myProfileInfo: MyProfileInfo = MyProfileInfo(),
        val selectedSportProfileId: String = "",
    ) {
        val activeProfile: ProfileInfo
            get() = myProfileInfo.myProfileInfo
    }
}

sealed interface MyPageUiState {
    data object Idle : MyPageUiState
    data object Loading : MyPageUiState
    data object Success : MyPageUiState
    data class Failure(
        val msg: String,
    ) : MyPageUiState
}
