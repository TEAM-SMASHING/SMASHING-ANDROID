package com.smashing.app.core.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smashing.app.R.font.pretendard_bold
import com.smashing.app.R.font.pretendard_medium
import com.smashing.app.R.font.pretendard_regular
import com.smashing.app.R.font.pretendard_semibold
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography

object PretendardFont {
    val Regular = FontFamily(Font(pretendard_regular))
    val Medium = FontFamily(Font(pretendard_medium))
    val SemiBold = FontFamily(Font(pretendard_semibold))
    val Bold = FontFamily(Font(pretendard_bold))
}

sealed interface TypographyTokens {
    @Immutable
    data class Hero(
        val bold28: TextStyle,
        val semibold28: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Xxl(
        val bold24: TextStyle,
        val semibold24: TextStyle,
        val medium24: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Xl(
        val semibold20: TextStyle,
        val medium20: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Lg(
        val semibold18: TextStyle,
        val medium18: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Md(
        val semibold16: TextStyle,
        val medium16: TextStyle,
        val regular16: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Sm(
        val semibold14: TextStyle,
        val medium14: TextStyle,
        val regular14: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Xs(
        val medium12: TextStyle,
        val regular12: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Xxs(
        val medium10: TextStyle,
        val regular10: TextStyle,
    ) : TypographyTokens
}

@Immutable
data class SmashingTypography(
    val hero: TypographyTokens.Hero,
    val xxl: TypographyTokens.Xxl,
    val xl: TypographyTokens.Xl,
    val lg: TypographyTokens.Lg,
    val md: TypographyTokens.Md,
    val sm: TypographyTokens.Sm,
    val xs: TypographyTokens.Xs,
    val xxs: TypographyTokens.Xxs,
)

val defaultSmashingTypography = SmashingTypography(
    hero = TypographyTokens.Hero(
        bold28 = TextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 28.sp,
            lineHeight = 28.sp * 1.5f,
            letterSpacing = 28.sp * -0.02f,
        ),
        semibold28 = TextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 28.sp,
            lineHeight = 28.sp * 1.5f,
            letterSpacing = 28.sp * -0.02f,
        ),
    ),
    xxl = TypographyTokens.Xxl(
        bold24 = TextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 24.sp,
            lineHeight = 24.sp * 1.5f,
            letterSpacing = 24.sp * -0.02f,
        ),
        semibold24 = TextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 24.sp,
            lineHeight = 24.sp * 1.5f,
            letterSpacing = 24.sp * -0.02f,
        ),
        medium24 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 24.sp,
            lineHeight = 24.sp * 1.5f,
            letterSpacing = 24.sp * -0.02f,
        ),
    ),
    xl = TypographyTokens.Xl(
        semibold20 = TextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 20.sp,
            lineHeight = 20.sp * 1.5f,
            letterSpacing = 20.sp * -0.02f,
        ),
        medium20 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 20.sp,
            lineHeight = 20.sp * 1.5f,
            letterSpacing = 20.sp * -0.02f,
        ),
    ),
    lg = TypographyTokens.Lg(
        semibold18 = TextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 18.sp,
            lineHeight = 18.sp * 1.5f,
            letterSpacing = 18.sp * -0.02f,
        ),
        medium18 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 18.sp,
            lineHeight = 18.sp * 1.5f,
            letterSpacing = 18.sp * -0.02f,
        ),
    ),
    md = TypographyTokens.Md(
        semibold16 = TextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 16.sp,
            lineHeight = 16.sp * 1.5f,
            letterSpacing = 16.sp * -0.01f,
        ),
        medium16 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 16.sp,
            lineHeight = 16.sp * 1.5f,
            letterSpacing = 16.sp * -0.01f,
        ),
        regular16 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 16.sp,
            lineHeight = 16.sp * 1.5f,
            letterSpacing = 16.sp * -0.01f,
        ),
    ),
    sm = TypographyTokens.Sm(
        semibold14 = TextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 14.sp,
            lineHeight = 14.sp * 1.5f,
            letterSpacing = 14.sp * -0.01f,
        ),
        medium14 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 14.sp,
            lineHeight = 14.sp * 1.5f,
            letterSpacing = 14.sp * -0.01f,
        ),
        regular14 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 14.sp,
            lineHeight = 14.sp * 1.5f,
            letterSpacing = 14.sp * -0.01f,
        ),
    ),
    xs = TypographyTokens.Xs(
        medium12 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 12.sp,
            lineHeight = 12.sp * 1.5f,
            letterSpacing = 0.sp,
        ),
        regular12 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 12.sp,
            lineHeight = 12.sp * 1.5f,
            letterSpacing = 0.sp,
        ),
    ),
    xxs = TypographyTokens.Xxs(
        medium10 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 10.sp,
            lineHeight = 10.sp * 1.5f,
            letterSpacing = 0.sp,
        ),
        regular10 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 10.sp,
            lineHeight = 10.sp * 1.5f,
            letterSpacing = 0.sp,
        ),
    ),
)

val LocalSmashingTypography = staticCompositionLocalOf { defaultSmashingTypography }

@Preview(showBackground = true)
@Composable
fun SmashingTypographyPreview() {
    SmashingAndroidTheme {
        Column {
            Text("hero.bold28", style = typography.hero.bold28)
            Text("hero.semibold28", style = typography.hero.semibold28)

            Spacer(modifier = Modifier.height(12.dp))

            Text("xxl.bold24", style = typography.xxl.bold24)
            Text("xxl.semibold24", style = typography.xxl.semibold24)
            Text("xxl.medium24", style = typography.xxl.medium24)

            Spacer(modifier = Modifier.height(12.dp))

            Text("xl.semibold20", style = typography.xl.semibold20)
            Text("xl.medium20", style = typography.xl.medium20)

            Spacer(modifier = Modifier.height(12.dp))

            Text("lg.semibold18", style = typography.lg.semibold18)
            Text("lg.medium18", style = typography.lg.medium18)

            Spacer(modifier = Modifier.height(12.dp))

            Text("md.semibold16", style = typography.md.semibold16)
            Text("md.medium16", style = typography.md.medium16)
            Text("md.regular16", style = typography.md.regular16)

            Spacer(modifier = Modifier.height(12.dp))

            Text("sm.semibold14", style = typography.sm.semibold14)
            Text("sm.medium14", style = typography.sm.medium14)
            Text("sm.regular14", style = typography.sm.regular14)

            Spacer(modifier = Modifier.height(12.dp))

            Text("xs.medium12", style = typography.xs.medium12)
            Text("xs.regular12", style = typography.xs.regular12)

            Spacer(modifier = Modifier.height(12.dp))

            Text("xxs.medium10", style = typography.xxs.medium10)
            Text("xxs.regular10", style = typography.xxs.regular10)
        }
    }
}