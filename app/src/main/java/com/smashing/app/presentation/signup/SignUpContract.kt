package com.smashing.app.presentation.signup

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType

interface SignUpContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val selectedGender: GenderType? = null,
        val selectedSport: SportType? = null,
        val selectedSkill: SkillType?= null,
        val locationInput: String = "",
    )

    sealed interface SideEffect {
        data object NavigateToHome: SideEffect
    }
}
