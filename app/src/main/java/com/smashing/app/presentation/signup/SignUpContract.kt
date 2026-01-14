package com.smashing.app.presentation.signup

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable

interface SignUpContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val nicknameInput: TextFieldState = TextFieldState(""),
        val selectedGender: String = "",
        val chatLinkInput: String = "",
        val selectedSport: String = "",
        val selectedSkill: String = "",
        val locationInput: String = "",
    )
}
