package com.smashing.app.core.designsystem.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.smashing.app.core.designsystem.theme.SmashingTheme

/**
 * ColoredBoxTextFieldStyle의 UI 상태를 정의하는 열거형 클래스입니다.
 * SmashingSearchTextField, ScoreInputTextField 컴포넌트에서 사용됩니다.
 *
 * @property ACTIVE 포커스 O, 입력값 O (수정 중인 상태)
 * @property TYPING 포커스 O, 입력값 X (입력 시작 전, 커서만 깜빡이는 상태)
 * @property INACTIVE 포커스 X, 입력값 X (초기 상태, Placeholder 노출)
 */

enum class ColoredBoxTextFieldStyle {
    ACTIVE, TYPING, INACTIVE;

    companion object {
        fun from(
            isFocused: Boolean,
            isFilled: Boolean,
        ): ColoredBoxTextFieldStyle {
            return when {
                isFilled -> ACTIVE
                isFocused -> TYPING
                else -> INACTIVE
            }
        }
    }

    @Composable
    fun getBackgroundColor(): Color = SmashingTheme.colors.bgSurface

    @Composable
    fun getContentColor(): Color = when (this) {
        ACTIVE, TYPING -> SmashingTheme.colors.txtPrimary
        INACTIVE -> SmashingTheme.colors.txtDisabled
    }

    @Composable
    fun getBorderColor(): Color = when (this) {
        ACTIVE -> Color.Transparent
        TYPING -> SmashingTheme.colors.borderTyping
        INACTIVE -> Color.Transparent
    }

    @Composable
    fun getTextStyle(): TextStyle = SmashingTheme.typography.sm.medium14
}
