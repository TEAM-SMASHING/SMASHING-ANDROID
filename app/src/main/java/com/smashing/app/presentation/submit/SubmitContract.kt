package com.smashing.app.presentation.submit

import androidx.compose.runtime.Immutable

interface SubmitContract {
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
    )

}
