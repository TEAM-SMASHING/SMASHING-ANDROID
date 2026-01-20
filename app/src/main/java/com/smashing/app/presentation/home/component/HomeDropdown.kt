package com.smashing.app.presentation.home.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.smashing.app.R.drawable.ic_plus
import com.smashing.app.core.designsystem.component.badge.TierBadge
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.progressbar.SmashingProgressBar
import com.smashing.app.core.designsystem.mapper.img
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.SmashingBtnColor
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.my.UserProfileItem
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Composable
fun HomeDropdown(
    activeSport: SportType,
    sportList: ImmutableList<UserProfileItem>,
    tierType: TierType,
    lp: Int,
    minLp: Int,
    maxLp: Int,
    winCount: Int,
    loseCount: Int,
    onSportChipClick: () -> Unit,
    onSportAddClick: () -> Unit,
    onTierClick: () -> Unit,
    onDismiss: () -> Unit,
    triggerHeight: Dp,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
) {
    val density = LocalDensity.current

    val transition = updateTransition(
        targetState = isExpanded,
        label = "HomeDropdown"
    )

    val yOffset by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = 150,
                    easing = LinearEasing
                )
            } else {
                tween(
                    durationMillis = 100,
                    easing = LinearEasing
                )
            }
        },
        label = "yOffset"
    ) { expanded ->
        if (expanded) 0f else -200f
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        },
        label = "alpha"
    ) { expanded ->
        if (expanded) 1f else 0f
    }

    if (isExpanded || alpha > 0f) {
        Popup(
            alignment = Alignment.TopStart,
            offset = IntOffset(
                x = 0,
                y = with(density) { (triggerHeight).toPx().toInt() }
            ),
            onDismissRequest = onDismiss,
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        translationY = yOffset,
                        alpha = alpha,
                    )
                    .background(
                        color = SmashingTheme.colors.bgSurface,
                        shape = RoundedCornerShape(
                            bottomStart = 30.dp,
                            bottomEnd = 30.dp,
                        ),
                    )
                    .padding(
                        bottom = 8.dp,
                    )
                    .padding(
                        horizontal = 16.dp,
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    sportList.forEach { sport ->
                        SmashingChip(
                            text = sport.sportCode.sportName,
                            style = if (sport.isActive) ChipStyle.ACTIVE else ChipStyle.DISABLED,
                            onClick = onSportChipClick,
                        )
                    }
                    SmashingChip(
                        icon = ImageVector.vectorResource(ic_plus),
                        style = ChipStyle.DISABLED,
                        onClick = onSportAddClick,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = tierType.img()),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .aspectRatio(1f)
                )

                TierBadge(
                    tierType = tierType.getNextTier(),
                    modifier = Modifier
                        .align(Alignment.End),
                )

                Spacer(modifier = Modifier.height(8.dp))

                SmashingProgressBar(
                    progress = (lp - minLp) / (maxLp - minLp).toFloat(),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = (maxLp - minLp).toString(),
                        style = SmashingTheme.typography.md.semibold16,
                        color = SmashingTheme.colors.txtPrimary,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "LP 남았어요!",
                        style = SmashingTheme.typography.md.semibold16,
                        color = SmashingTheme.colors.txtTertiary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "LP",
                        style = SmashingTheme.typography.md.semibold16,
                        color = SmashingTheme.colors.txtTertiary,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = maxLp.toString(),
                        style = SmashingTheme.typography.md.semibold16,
                        color = SmashingTheme.colors.txtPrimary,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                SmashingBaseButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "티어 설명",
                    textStyle = SmashingTheme.typography.lg.semibold18,
                    onClick = onTierClick,
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

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(
                            horizontal = 14.dp,
                            vertical = 12.dp,
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MatchRecordItem(
                        title = winCount.toString(),
                        subTitle = "승리",
                        contentColor = SmashingTheme.colors.txtEmphasis,
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        modifier = Modifier.width(1.dp),
                        color = SmashingTheme.colors.borderSecondary,
                    )
                    MatchRecordItem(
                        title = loseCount.toString(),
                        subTitle = "패배",
                        contentColor = SmashingTheme.colors.txtRed,
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        modifier = Modifier.width(1.dp),
                        color = SmashingTheme.colors.borderSecondary,
                    )
                    MatchRecordItem(
                        title = "${winRate(winCount = winCount, loseCount = loseCount)}%",
                        subTitle = "승률",
                        contentColor = SmashingTheme.colors.txtPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchRecordItem(
    title: String,
    subTitle: String,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            color = contentColor,
            style = SmashingTheme.typography.xl.semibold20,
        )
        Text(
            text = subTitle,
            color = SmashingTheme.colors.txtSecondary,
            style = SmashingTheme.typography.xs.medium12,
        )
    }
}

private fun winRate(winCount: Int, loseCount: Int): Int {
    val totalCount = winCount + loseCount
    val winRate = if (totalCount == 0) {
        0
    } else {
        ((winCount.toFloat() / totalCount) * 100).toInt()
    }
    return winRate
}

@Preview(showBackground = true)
@Composable
private fun HomeDropdownPreview() {
    SmashingAndroidTheme {
        Box(
            modifier = Modifier
                .background(
                    color = SmashingTheme.colors.bgSurface
                )
        ) {
            HomeDropdown(
                activeSport = SportType.TENNIS,
                sportList = listOf(
                    UserProfileItem(
                        profileId = "0USP111222333",
                        sportCode = SportType.TENNIS,
                        isActive = true,
                    ),
                    UserProfileItem(
                        profileId = "0USP111222333",
                        sportCode = SportType.PING_PONG,
                        isActive = false,
                    ),
                    UserProfileItem(
                        profileId = "0USP111222333",
                        sportCode = SportType.BADMINTON,
                        isActive = false,
                    ),
                ).toImmutableList(),
                tierType = TierType.GOLD_1,
                lp = 123,
                minLp = 100,
                maxLp = 500,
                winCount = 10,
                loseCount = 7,
                onSportChipClick = {},
                onSportAddClick = {},
                onTierClick = {},
                onDismiss = {},
                triggerHeight = 50.dp,
                isExpanded = true,
            )
        }
    }
}