package com.smashing.app.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Gray Scale
val gray50 = Color(0xFFF9F9FD)
val gray100 = Color(0xFFF0F1F4)
val gray200 = Color(0xFFE2E6EA)
val gray300 = Color(0xFFBFC5D0)
val gray400 = Color(0xFF9FA7B7)
val gray500 = Color(0xFF576071)
val gray600 = Color(0xFF363C4B)
val gray700 = Color(0xFF252A36)
val gray800 = Color(0xFF21252F)
val gray900 = Color(0xFF1A1E22)

// Base
val white = Color(0xFFFFFFFF)
val black = Color(0xFF080808)

// Primary
val primary100 = Color(0xFFD4E7FF)
val primary200 = Color(0xFF2261FF)
val primary300 = Color(0xFF1B2F51)

// Sub Color
val redMain = Color(0xFFFD344D)
val redSub = Color(0xFF4E0B0B)

val orangeMain = Color(0xFFFF8412)
val orangeSub = Color(0xFF4E2F0B)

val yellowMain = Color(0xFFD1CC44)
val yellowSub = Color(0xFF4E460B)
val yellowKakao = Color(0xFFFEE500)

val greenMain = Color(0xFF0DD74D)
val greenSub = Color(0xFF0B4E2F)

val mintMain = Color(0xFF4AB6D3)
val mintSub = Color(0xFF0B454E)

val purpleMain = Color(0xFF9059EF)
val purpleSub = Color(0xFF340B4E)

val brownMain = Color(0xFF8E5C1C)
val brownSub = Color(0xFF3D3120)

val ashbrnMain = Color(0xFF9F9189)
val ashbrnSub = Color(0xFF3A2F2C)

val gold = Color(0xFFFFD700)
val silver = Color(0xFFC9CCD6)
val copper = Color(0xFFC07A3A)
val alpha = Color(0x80000000)

@Immutable
data class SmashingColors(
    // Text
    val txtPrimary: Color,
    val txtPrimaryReverse: Color,
    val txtSecondary: Color,
    val txtTertiary: Color,
    val txtDisabled: Color,
    val txtEmphasis: Color,
    val txtMuted: Color,
    val txtRed: Color,
    val txtKakaoLinkGray: Color,

    // Background
    val bgCanvas: Color,
    val bgCanvasReverse: Color,
    val bgSurface: Color,
    val bgSurfacePressed: Color,
    val bgOverlay: Color,
    val bgSelected: Color,
    val bgDimmed: Color,
    val bgKakao: Color,

    // Button
    val btnTxtPrimaryActive: Color,
    val btnTxtPrimaryDisabled: Color,
    val btnTxtPrimaryPressed: Color,
    val btnTxtSecondaryActive: Color,
    val btnTxtRejected: Color,
    val btnBgPrimaryActive: Color,
    val btnBgPrimaryDisabled: Color,
    val btnBgPrimaryPressed: Color,
    val btnBgSecondaryActive: Color,
    val btnBgTertiaryActive: Color,
    val btnBgTertiaryPressed: Color,
    val btnBgRejected: Color,
    val btnBgPrimary300: Color,

    // Border
    val borderPrimary: Color,
    val borderSecondary: Color,
    val borderTertiary: Color,
    val borderTyping: Color,
    val borderError: Color,

    // Icon
    val iconPrimary: Color,
    val iconPrimaryReverse: Color,
    val iconSecondary: Color,
    val iconTertiary: Color,
    val iconError: Color,
    val iconSuccess: Color,
    val iconActive: Color,
    val iconInactive: Color,
    val iconNotification: Color,
    val iconMedalGold: Color,
    val iconMedalSilver: Color,
    val iconMedalCopper: Color,
    val iconCrown: Color,

    // Tier
    val tierIronTxt: Color,
    val tierIronBg: Color,
    val tierBronzeTxt: Color,
    val tierBronzeBg: Color,
    val tierSilverTxt: Color,
    val tierSilverBg: Color,
    val tierGoldTxt: Color,
    val tierGoldBg: Color,
    val tierPlatinumTxt: Color,
    val tierPlatinumBg: Color,
    val tierDiamondTxt: Color,
    val tierDiamondBg: Color,
    val tierChallengerTxt: Color,
    val tierChallengerBg: Color,

    // State
    val stateCheck: Color,
    val stateWarning: Color,
    val stateSuccess: Color,
    val stateProgressTrack: Color,
    val stateProgressFill: Color,
)

val defaultSmashingColors = SmashingColors(
    // Text
    txtPrimary = white,
    txtPrimaryReverse = black,
    txtSecondary = gray200,
    txtTertiary = gray400,
    txtDisabled = gray500,
    txtEmphasis = primary200,
    txtMuted = primary100,
    txtRed = redMain,
    txtKakaoLinkGray = gray300,

    // Background
    bgCanvas = black,
    bgCanvasReverse = gray200,
    bgSurface = gray900,
    bgSurfacePressed = gray800,
    bgOverlay = gray700,
    bgSelected = white,
    bgDimmed = alpha,
    bgKakao = yellowKakao,

    // Button
    btnTxtPrimaryActive = black,
    btnTxtPrimaryDisabled = gray500,
    btnTxtPrimaryPressed = gray300,
    btnTxtSecondaryActive = white,
    btnTxtRejected = redMain,
    btnBgPrimaryActive = white,
    btnBgPrimaryDisabled = gray800,
    btnBgPrimaryPressed = gray600,
    btnBgSecondaryActive = primary200,
    btnBgTertiaryActive = gray600,
    btnBgTertiaryPressed = gray800,
    btnBgRejected = redSub,
    btnBgPrimary300 = primary300,

    // Border
    borderPrimary = gray700,
    borderSecondary = gray600,
    borderTertiary = gray200,
    borderTyping = primary100,
    borderError = redMain,

    // Icon
    iconPrimary = white,
    iconPrimaryReverse = black,
    iconSecondary = gray300,
    iconTertiary = gray500,
    iconError = redMain,
    iconSuccess = primary100,
    iconActive = gray50,
    iconInactive = gray500,
    iconNotification = redMain,
    iconMedalGold = gold,
    iconMedalSilver = silver,
    iconMedalCopper = copper,
    iconCrown = gold,

    // Tier
    tierIronTxt = ashbrnMain,
    tierIronBg = ashbrnSub,
    tierBronzeTxt = brownMain,
    tierBronzeBg = brownSub,
    tierSilverTxt = gray200,
    tierSilverBg = gray600,
    tierGoldTxt = yellowMain,
    tierGoldBg = yellowSub,
    tierPlatinumTxt = mintMain,
    tierPlatinumBg = mintSub,
    tierDiamondTxt = primary100,
    tierDiamondBg = primary300,
    tierChallengerTxt = purpleMain,
    tierChallengerBg = purpleSub,

    // State
    stateCheck = primary100,
    stateWarning = redMain,
    stateSuccess = greenMain,
    stateProgressTrack = gray700,
    stateProgressFill = primary200,
)
val LocalSmashingColors = staticCompositionLocalOf { defaultSmashingColors }

@Preview(showBackground = true)
@Composable
private fun SmashingColorsPreview() {
    SmashingAndroidTheme {
        Column {
            listOf(
                // Base
                white,
                black,

                // Gray Scale
                gray50,
                gray100,
                gray200,
                gray300,
                gray400,
                gray500,
                gray600,
                gray700,
                gray800,
                gray900,

                // Primary
                primary100,
                primary200,
                primary300,

                // Sub Color
                redMain,
                redSub,
                orangeMain,
                orangeSub,
                yellowMain,
                yellowSub,
                greenMain,
                greenSub,
                mintMain,
                mintSub,
                purpleMain,
                purpleSub,
                brownMain,
                brownSub,
                ashbrnMain,
                ashbrnSub,

                gold,
                silver,
                copper,
                alpha,
            ).chunked(6).forEach { rowColors ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    rowColors.forEach { c ->
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(c)
                                .padding(end = 8.dp),
                        )
                    }
                }
            }
        }
    }
}
