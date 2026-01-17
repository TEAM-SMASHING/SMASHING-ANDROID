package com.smashing.app.core.designsystem.style

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.smashing.app.R.drawable.img_tier_dummy
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.type.TierType

enum class TierInfo(
    val tierName: String,
) {
    IRON(
        tierName = "Iron",
    ),
    BRONZE(
        tierName = "Bronze",
    ),
    SILVER(
        tierName = "Silver",
    ),
    GOLD(
        tierName = "Gold",
    ),
    PLATINUM(
        tierName = "Platinum",
    ),
    DIAMOND(
        tierName = "Diamond",
    ),
    CHALLENGER(
        tierName = "Challenger",
    );

    @ReadOnlyComposable
    @Composable
    fun getTxtColor(): Color = when (this) {
        IRON -> SmashingTheme.colors.tierIronTxt
        BRONZE -> SmashingTheme.colors.tierBronzeTxt
        SILVER -> SmashingTheme.colors.tierSilverTxt
        GOLD -> SmashingTheme.colors.tierGoldTxt
        PLATINUM -> SmashingTheme.colors.tierPlatinumTxt
        DIAMOND -> SmashingTheme.colors.tierDiamondTxt
        CHALLENGER -> SmashingTheme.colors.tierChallengerTxt
    }

    @DrawableRes
    fun getImg(): Int = when (this) {
        IRON -> img_tier_dummy
        BRONZE -> img_tier_dummy
        SILVER -> img_tier_dummy
        GOLD -> img_tier_dummy
        PLATINUM -> img_tier_dummy
        DIAMOND -> img_tier_dummy
        CHALLENGER -> img_tier_dummy
    }
}

fun TierType.toTierInfo(): TierInfo {
    return when (this) {
        TierType.IRON -> TierInfo.IRON
        TierType.BRONZE_3,
        TierType.BRONZE_2,
        TierType.BRONZE_1 -> TierInfo.BRONZE
        TierType.SILVER_3,
        TierType.SILVER_2,
        TierType.SILVER_1 -> TierInfo.SILVER
        TierType.GOLD_3,
        TierType.GOLD_2,
        TierType.GOLD_1 -> TierInfo.GOLD
        TierType.PLATINUM_3,
        TierType.PLATINUM_2,
        TierType.PLATINUM_1 -> TierInfo.PLATINUM
        TierType.DIAMOND_3,
        TierType.DIAMOND_2,
        TierType.DIAMOND_1 -> TierInfo.DIAMOND
        TierType.CHALLENGER -> TierInfo.CHALLENGER
    }
}