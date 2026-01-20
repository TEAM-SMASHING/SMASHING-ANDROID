package com.smashing.app.core.designsystem.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.smashing.app.R.string.matching_btn_canceled
import com.smashing.app.R.string.matching_btn_confirm
import com.smashing.app.R.string.matching_btn_rejected
import com.smashing.app.R.string.matching_btn_unknown
import com.smashing.app.R.string.matching_btn_waiting_confirm
import com.smashing.app.R.string.matching_btn_write
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.data.type.GameResultStatusType

enum class ButtonStyle {
    PRIMARY,
    PRIMARY_WITH_DISABLED,
    SECONDARY,
    DISABLED_ACTIVE,
    REJECTED,
    TERTIARY;

    @ReadOnlyComposable
    @Composable
    fun getButtonColor(): SmashingBtnColor = when (this) {
        PRIMARY -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryActive,
            textColor = colors.btnTxtPrimaryActive,
        )

        PRIMARY_WITH_DISABLED -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryActive,
            textColor = colors.btnTxtPrimaryActive,
            disabledBackgroundColor = colors.btnBgPrimaryDisabled,
            disabledTextColor = colors.btnTxtPrimaryDisabled,
        )

        SECONDARY -> SmashingBtnColor(
            backgroundColor = colors.btnBgSecondaryActive,
            textColor = colors.btnTxtSecondaryActive,
        )

        DISABLED_ACTIVE -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryDisabled,
            textColor = colors.btnTxtSecondaryActive,
        )

        REJECTED -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryActive,
            textColor = colors.btnTxtRejected,
        )

        TERTIARY -> SmashingBtnColor(
            backgroundColor = colors.btnBgTertiaryPressed,
            textColor = colors.btnTxtPrimaryPressed,
        )
    }
}

@Composable
fun GameResultStatusType.getMatchButtonColor() = when(this) {
    GameResultStatusType.PENDING_RESULT -> SmashingBtnColor(
        backgroundColor = colors.btnBgPrimaryActive,
        textColor = colors.txtEmphasis,
    )

    GameResultStatusType.RESULT_REJECTED -> SmashingBtnColor(
        backgroundColor = colors.btnBgRejected,
        textColor = colors.btnTxtRejected,
    )

    GameResultStatusType.RESULT_CONFIRMED -> SmashingBtnColor(
        backgroundColor = colors.btnBgPrimary300,
        textColor = colors.txtEmphasis,
    )

    GameResultStatusType.WAITING_CONFIRMATION -> SmashingBtnColor(
        backgroundColor = colors.btnBgPrimaryDisabled,
        textColor = colors.btnTxtPrimaryDisabled
    )

    GameResultStatusType.CANCELED -> SmashingBtnColor(
        backgroundColor = colors.btnBgPrimaryDisabled,
        textColor = colors.btnTxtPrimaryDisabled
    )

    GameResultStatusType.UNKNOWN -> SmashingBtnColor(
        backgroundColor = colors.btnBgPrimaryDisabled,
        textColor = colors.btnTxtPrimaryDisabled
    )
}

@Composable
fun GameResultStatusType.getMatchButtonTitle() = when(this) {
    GameResultStatusType.PENDING_RESULT -> stringResource(matching_btn_write)
    GameResultStatusType.RESULT_REJECTED -> stringResource(matching_btn_rejected)
    GameResultStatusType.RESULT_CONFIRMED -> stringResource(matching_btn_confirm)
    GameResultStatusType.WAITING_CONFIRMATION -> stringResource(matching_btn_waiting_confirm)
    GameResultStatusType.CANCELED -> stringResource(matching_btn_canceled)
    GameResultStatusType.UNKNOWN -> stringResource(matching_btn_unknown)
}

@Immutable
data class SmashingBtnColor(
    val backgroundColor: Color,
    val textColor: Color,
    val disabledBackgroundColor: Color = backgroundColor,
    val disabledTextColor: Color = textColor,
)
