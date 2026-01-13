package com.smashing.app.presentation.submit

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable

interface SubmitContract {
    val TextFieldState.intValue: Int
        get() = text.toString().toIntOrNull() ?: 0

    @Immutable
    data class State(
        val submitterName: String = "",
        val receiverName: String = "",
        val submitterScore: Int = 0,
        val receiverScore: Int = 0,
        val submitterUserId: String = "",
        val receiverUserId: String = "",
        val winnerUserId: String? = null,
        val loserUserId: String? = null,
        val selectedDropdownItem: String? = null,
        val isButtonEnabled: Boolean = false,
        val leftTextFieldState: TextFieldState = TextFieldState(),
        val rightTextFieldState: TextFieldState = TextFieldState(),
    )

}
