package com.smashing.app.presentation.signup

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.type.SportType

interface SignUpContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val nicknameInput: TextFieldState = TextFieldState(""),
        val selectedGender: String = "",
        val chatLinkInput: String = "",
        val selectedSport: SportType? = null,
        val selectedSkill: String = "",
        val locationInput: String = "",
    )
}
