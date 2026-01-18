package com.smashing.app.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_fake_red
import com.smashing.app.R.drawable.ic_plus
import com.smashing.app.R.string.lp_remaining_text
import com.smashing.app.R.string.lp_status
import com.smashing.app.R.string.tier_description
import com.smashing.app.core.designsystem.component.badge.TierBadge
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.progressbar.SmashingProgressBar
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.SmashingBtnColor
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ProfileTierBox(
    tierType: TierType,
    sportProfileList: ImmutableList<SportProfile>,
    tierIconResId: Int,
    progress: Float,
    lpStatus: Int,
    totalLp: Int,
    selectedProfileId: String,
    modifier: Modifier = Modifier,
    onTierInfoClick: (() -> Unit)? = null,
    onAddSportClick: (() -> Unit)? = null,
    onSportClick: ((String) -> Unit)? = null,
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
                sportProfileList.forEach { sport ->
                    val isSelected = sport.profileId == selectedProfileId

                    SmashingChip(
                        text = sport.sportType.sportName,
                        style = if (isSelected) ChipStyle.ACTIVE else ChipStyle.DISABLED,
                        onClick = { onSportClick?.invoke(sport.profileId) },
                    )

                    Spacer(modifier = Modifier.width(7.dp))
                }

                if (onAddSportClick != null) {
                    SmashingChip(
                        style = ChipStyle.DISABLED,
                        icon = ImageVector.vectorResource(id = ic_plus),
                        onClick = onAddSportClick,
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Icon(
                painter = painterResource(id = tierIconResId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f),
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
                    color = SmashingTheme.colors.txtPrimary,
                    style = SmashingTheme.typography.md.medium16,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            if (onTierInfoClick != null) {
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
}

@Preview
@Composable
private fun ProfileTierBoxPreview() {
    SmashingAndroidTheme {
        ProfileTierBox(
            tierType = TierType.GOLD_1,
            sportProfileList = persistentListOf(
                SportProfile(
                    profileId = "1",
                    sportType = SportType.PING_PONG,
                    isActive = true,
                ),
                SportProfile(
                    profileId = "1",
                    sportType = SportType.PING_PONG,
                    isActive = false,
                ),
                SportProfile(
                    profileId = "1",
                    sportType = SportType.PING_PONG,
                    isActive = false,
                ),
            ),
            tierIconResId = ic_fake_red,
            progress = 0.4f,
            lpStatus = 100,
            totalLp = 500,
            onTierInfoClick = {},
            onAddSportClick = {},
            onSportClick = {},
            selectedProfileId = "",
        )
    }
}
