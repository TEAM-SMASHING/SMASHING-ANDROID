package com.smashing.app.presentation.signup

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType
import com.smashing.app.domain.model.Region
import com.smashing.app.presentation.home.regionchange.RegionChangeContract
import com.smashing.app.presentation.home.regionchange.RegionChangeUiState
import com.smashing.app.presentation.region.RegionContract

interface SignUpContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val nickNameErrorText: String? = null,
        val nickNameConfirmText: String? = null,
        val isNickNameAvailable: Boolean = false,
        val openChatErrorText: String? = null,
        val isOpenChatValid: Boolean = false,
        val selectedGender: GenderType? = null,
        val selectedSport: SportType? = null,
        val selectedSkill: SkillType?= null,
        val selectedRegion: Region? = null,
        val regionLoadState: SignUpUiState = SignUpUiState.Idle,
    )

    sealed interface SideEffect {
        data object NavigateToHome: SideEffect
        data object NavigateToRegion : SideEffect
    }

    sealed interface SignUpUiState {
        object Idle : SignUpUiState

        object Success : SignUpUiState

        data class Failure(
            val msg: String,
        ) : SignUpUiState
    }
}
