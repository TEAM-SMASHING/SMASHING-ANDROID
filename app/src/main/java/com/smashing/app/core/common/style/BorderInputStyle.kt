package com.smashing.app.core.common.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.smashing.app.core.designsystem.theme.SmashingTheme

/**
 * BorderInputStyle의 UI 상태를 정의하는 열거형 클래스입니다.
 * TextAreaTextField, NicknameInputTextField,InputTextField 컴포넌트에서 사용됩니다.
 *
 * @property ERROR 유효성 검증 실패 (예: 중복된 닉네임, 형식 오류)
 * @property CONFIRM 유효성 검증 성공 (예: 사용 가능한 닉네임)
 * @property ACTIVE 포커스 O, 입력값 O (수정 중인 상태)
 * @property TYPING 포커스 O, 입력값 X (입력 시작 전, 커서만 깜빡이는 상태)
 * @property INACTIVE 포커스 X, 입력값 X (초기 상태, Placeholder 노출)
 */

enum class BorderInputStyle {
    ERROR, CONFIRM, ACTIVE, TYPING, INACTIVE;

    companion object {
        fun from(
            isFocused: Boolean,
            isFilled: Boolean,
            isError: Boolean,
            isConfirm: Boolean
        ): BorderInputStyle {
            return when {
                isError -> ERROR
                isConfirm -> CONFIRM
                !isFocused && isFilled -> ACTIVE
                isFocused -> TYPING
                else -> INACTIVE
            }
        }
    }

    @Composable
    fun getContentColor(): Color = when (this) {
        ERROR, CONFIRM, ACTIVE, TYPING -> SmashingTheme.colors.txtPrimary
        INACTIVE -> SmashingTheme.colors.txtDisabled
    }

    @Composable
    fun getBorderColor(): Color = when (this) {
        ERROR -> SmashingTheme.colors.borderError
        CONFIRM, ACTIVE, INACTIVE -> SmashingTheme.colors.borderSecondary
        TYPING -> SmashingTheme.colors.borderTyping
    }

    @Composable
    fun getTextStyle(): TextStyle = SmashingTheme.typography.sm.medium14
}
