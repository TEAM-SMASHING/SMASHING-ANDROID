package com.smashing.app.presentation.signup

import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.SkillType
import com.smashing.app.core.common.type.SportType

interface SignUpContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val nickNameErrorText: String? = null,
        val nickNameConfirmText: String? = null,
        val selectedGender: GenderType? = null,
        val selectedSport: SportType? = null,
        val selectedSkill: SkillType?= null,
        val locationInput: String = "",
    )

    sealed interface SideEffect {
        data object NavigateToHome: SideEffect
    }
}
