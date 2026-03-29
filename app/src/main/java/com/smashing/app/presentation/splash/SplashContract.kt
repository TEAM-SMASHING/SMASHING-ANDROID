package com.smashing.app.presentation.splash

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType
import com.smashing.app.domain.model.Region
import com.smashing.app.presentation.signup.SignUpUiState

interface SplashContract {

    sealed interface SideEffect {
        data object NavigateToHome: SideEffect
        data object NavigateToLogin: SideEffect
    }

}
