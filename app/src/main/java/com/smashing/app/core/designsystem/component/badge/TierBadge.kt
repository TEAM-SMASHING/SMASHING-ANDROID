package com.smashing.app.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.common.type.TierType.BRONZE_1
import com.smashing.app.core.common.type.TierType.BRONZE_2
import com.smashing.app.core.common.type.TierType.BRONZE_3
import com.smashing.app.core.common.type.TierType.CHALLENGER
import com.smashing.app.core.common.type.TierType.DIAMOND_1
import com.smashing.app.core.common.type.TierType.DIAMOND_2
import com.smashing.app.core.common.type.TierType.DIAMOND_3
import com.smashing.app.core.common.type.TierType.GOLD_1
import com.smashing.app.core.common.type.TierType.GOLD_2
import com.smashing.app.core.common.type.TierType.GOLD_3
import com.smashing.app.core.common.type.TierType.IRON
import com.smashing.app.core.common.type.TierType.PLATINUM_1
import com.smashing.app.core.common.type.TierType.PLATINUM_2
import com.smashing.app.core.common.type.TierType.PLATINUM_3
import com.smashing.app.core.common.type.TierType.SILVER_1
import com.smashing.app.core.common.type.TierType.SILVER_2
import com.smashing.app.core.common.type.TierType.SILVER_3
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography

@Composable
fun TierBadge(
    tierType: TierType,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, textColor) = getTierBadgeColors(tierType)

    Text(
        text = tierType.tierName,
        style = typography.xs.regular12,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier
            .width(67.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(2.dp),
            )
            .padding(vertical = 3.dp),
    )
}

@ReadOnlyComposable
@Composable
private fun getTierBadgeColors(
    tierType: TierType,
): Pair<Color, Color> = when (tierType) {
    BRONZE_1, BRONZE_2, BRONZE_3 -> colors.tierBronzeBg to colors.tierBronzeTxt
    SILVER_1, SILVER_2, SILVER_3 -> colors.tierSilverBg to colors.tierSilverTxt
    GOLD_1, GOLD_2, GOLD_3 -> colors.tierGoldBg to colors.tierGoldTxt
    PLATINUM_1, PLATINUM_2, PLATINUM_3 -> colors.tierPlatinumBg to colors.tierPlatinumTxt
    DIAMOND_1, DIAMOND_2, DIAMOND_3 -> colors.tierDiamondBg to colors.tierDiamondTxt
    CHALLENGER -> colors.tierChallengerBg to colors.tierChallengerTxt
    IRON -> colors.tierIronBg to colors.tierIronTxt
}

@Preview(showBackground = true)
@Composable
private fun TierBadgePreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TierBadge(tierType = BRONZE_1)
            TierBadge(SILVER_1)
            TierBadge(GOLD_1)
            TierBadge(PLATINUM_1)
            TierBadge(DIAMOND_1)
            TierBadge(CHALLENGER)
            TierBadge(IRON)
        }
    }
}
