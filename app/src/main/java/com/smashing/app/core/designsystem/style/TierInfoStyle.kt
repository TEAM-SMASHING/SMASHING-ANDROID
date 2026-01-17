package com.smashing.app.core.designsystem.style

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.smashing.app.R.drawable.img_tier_dummy
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.type.TierType

enum class TierInfoStyle(
    val id: Int,
    val tierName: String,
    val tierKName: String,
) {
    IRON(
        id = 1,
        tierName = "Iron",
        tierKName = "아이언",
    ),
    BRONZE(
        id = 2,
        tierName = "Bronze",
        tierKName = "브론즈",
    ),
    SILVER(
        id = 3,
        tierName = "Silver",
        tierKName = "실버",
    ),
    GOLD(
        id = 4,
        tierName = "Gold",
        tierKName = "골드",
    ),
    PLATINUM(
        id = 5,
        tierName = "Platinum",
        tierKName = "플래티넘",
    ),
    DIAMOND(
        id = 6,
        tierName = "Diamond",
        tierKName = "다이아",
    ),
    CHALLENGER(
        id = 7,
        tierName = "Challenger",
        tierKName = "챌린저"
    );

    companion object {
        private val ID_MAP: Map<String, TierInfoStyle> = TierInfoStyle.entries.associateBy { it.tierKName }
        fun findTierInfo(tierKName: String?): TierInfoStyle? = ID_MAP[tierKName]
    }

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

fun TierType.toTierInfoStyle(): TierInfoStyle {
    return when (this) {
        TierType.IRON -> TierInfoStyle.IRON
        TierType.BRONZE_3,
        TierType.BRONZE_2,
        TierType.BRONZE_1 -> TierInfoStyle.BRONZE
        TierType.SILVER_3,
        TierType.SILVER_2,
        TierType.SILVER_1 -> TierInfoStyle.SILVER
        TierType.GOLD_3,
        TierType.GOLD_2,
        TierType.GOLD_1 -> TierInfoStyle.GOLD
        TierType.PLATINUM_3,
        TierType.PLATINUM_2,
        TierType.PLATINUM_1 -> TierInfoStyle.PLATINUM
        TierType.DIAMOND_3,
        TierType.DIAMOND_2,
        TierType.DIAMOND_1 -> TierInfoStyle.DIAMOND
        TierType.CHALLENGER -> TierInfoStyle.CHALLENGER
    }
}

fun String.toTierInfoStyle(): TierInfoStyle {
    return TierInfoStyle.entries.find { it.name == this } ?: TierInfoStyle.IRON
}
