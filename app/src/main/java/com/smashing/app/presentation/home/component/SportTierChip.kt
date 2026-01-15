package com.smashing.app.presentation.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.icon.icon
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

@Composable
fun SportsTierChip(
    sportType: SportType,
    tierType: TierType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .border(
                width = 1.dp,
                color = SmashingTheme.colors.borderSecondary,
                shape = RoundedCornerShape(999.dp),
            )
            .noRippleClickable(
                onClick = onClick,
            )
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp,
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(sportType.icon()),
            contentDescription = null,
            tint = SmashingTheme.colors.iconSuccess,
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = tierType.tierName,
            style = SmashingTheme.typography.sm.medium14,
            color = SmashingTheme.colors.txtMuted,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SportsTierChipPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SportsTierChip(
            sportType = SportType.PING_PONG,
            tierType = TierType.GOLD_1,
            onClick = {},
        )
        SportsTierChip(
            sportType = SportType.TENNIS,
            tierType = TierType.PLATINUM_2,
            onClick = {},
        )
        SportsTierChip(
            sportType = SportType.BADMINTON,
            tierType = TierType.DIAMOND_1,
            onClick = {},
        )
    }
}
