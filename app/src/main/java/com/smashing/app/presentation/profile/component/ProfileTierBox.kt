package com.smashing.app.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_fake_red
import com.smashing.app.R.string.add_sports_label
import com.smashing.app.R.string.lp_remaining_text
import com.smashing.app.R.string.lp_status
import com.smashing.app.R.string.tier_description
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.designsystem.component.badge.TierBadge
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.progressbar.SmashingProgressBar
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.SmashingBtnColor
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme


@Composable
fun ProfileTierBox(
    tierType: TierType,
    sports: List<SportType>,
    tierIconResId: Int,
    progress: Float,
    lpStatus: Int,
    totalLp: Int,
    onTierInfoClick: () -> Unit,
    selectedSport: SportType,
    onSportClick: (SportType) -> Unit,
    onAddSportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SmashingTheme.colors.bgSurface)
            .padding(vertical = 20.dp, horizontal = 16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                sports.forEach { sport ->
                    val isSelected = sport == selectedSport
                    SmashingChip(
                        text = sport.sportName,
                        style = if (isSelected) ChipStyle.ACTIVE else ChipStyle.INACTIVE,
                        onClick = { onSportClick(sport) },
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                }

                SmashingChip(
                    text = stringResource(id = add_sports_label),
                    style = ChipStyle.INACTIVE,
                    onClick = onAddSportClick,
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            Icon(
                imageVector = ImageVector.vectorResource(id = tierIconResId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(100.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ) {
                TierBadge(tierType.getNextTier())
            }

            Spacer(modifier = Modifier.height(8.dp))


            SmashingProgressBar(progress = progress)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = lpStatus.toString(),
                    color = SmashingTheme.colors.txtPrimary,
                    style = SmashingTheme.typography.md.semibold16,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(lp_remaining_text),
                    color = SmashingTheme.colors.txtTertiary,
                    style = SmashingTheme.typography.md.medium16,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(lp_status),
                    color = SmashingTheme.colors.txtTertiary,
                    style = SmashingTheme.typography.md.medium16,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = totalLp.toString(),
                    color = SmashingTheme.colors.txtTertiary,
                    style = SmashingTheme.typography.md.medium16,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            SmashingBaseButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(tier_description),
                textStyle = SmashingTheme.typography.lg.semibold18,
                onClick = onTierInfoClick,
                buttonColor = SmashingBtnColor(
                    backgroundColor = SmashingTheme.colors.tierDiamondBg,
                    textColor = SmashingTheme.colors.txtEmphasis,
                    disabledBackgroundColor = SmashingTheme.colors.tierDiamondBg,
                    disabledTextColor = SmashingTheme.colors.txtEmphasis,
                ),
                contentPadding = PaddingValues(vertical = 10.dp),
                shape = RoundedCornerShape(8.dp),
                isRippleEnabled = false,
            )
        }
    }
}

@Preview
@Composable
private fun ProfileTierBoxPreview() {
    SmashingAndroidTheme {
        ProfileTierBox(
            tierType = TierType.GOLD_1,
            sports = listOf(SportType.PING_PONG, SportType.TENNIS),
            tierIconResId = ic_fake_red,
            progress = 0.2f,
            lpStatus = 100,
            totalLp = 500,
            onTierInfoClick = {},
            onAddSportClick = {},
            onSportClick = {},
            selectedSport = SportType.PING_PONG,
        )
    }
}


