package com.smashing.app.core.designsystem.mapper

import androidx.annotation.DrawableRes
import com.smashing.app.R
import com.smashing.app.data.type.TierType

@DrawableRes
fun TierType.img() = when(this){
    TierType.IRON -> R.drawable.img_tier_sliver
    TierType.BRONZE_3,
    TierType.BRONZE_2,
    TierType.BRONZE_1 -> R.drawable.img_tier_bronze
    TierType.SILVER_3,
    TierType.SILVER_2,
    TierType.SILVER_1 -> R.drawable.img_tier_sliver
    TierType.GOLD_3,
    TierType.GOLD_2,
    TierType.GOLD_1 -> R.drawable.img_tier_gold
    TierType.PLATINUM_3,
    TierType.PLATINUM_2,
    TierType.PLATINUM_1 -> R.drawable.img_tier_platinum
    TierType.DIAMOND_3,
    TierType.DIAMOND_2,
    TierType.DIAMOND_1 -> R.drawable.img_tier_diamond
    TierType.CHALLENGER -> R.drawable.img_tier_challenger
}