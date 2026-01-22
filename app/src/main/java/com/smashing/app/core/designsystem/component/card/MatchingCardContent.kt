package com.smashing.app.core.designsystem.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smashing.app.R.string.count
import com.smashing.app.R.string.record
import com.smashing.app.R.string.review
import com.smashing.app.R.string.win_lose_count
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.component.badge.TierBadge
import com.smashing.app.core.designsystem.mapper.icon20
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.util.ProfileImageProvider

@Composable
fun MatchingCardContent(
    cardState: MatchingCardState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UrlImage(
            placeholderDrawable = ProfileImageProvider.getTempImg(cardState.nickname),
            modifier = Modifier
                .height(52.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .let { base ->
                    if (cardState is MatchingCardState.Search) {
                        base
                    } else {
                        base.noRippleClickable(onClick = cardState.onProfileClick)
                    }
                },
        )

        UserInfo(
            nickname = cardState.nickname,
            genderIcon = cardState.genderType.icon20(),
            modifier = Modifier.padding(
                vertical = 4.dp,
            )
        )

        TierBadge(
            tierType = cardState.tierType,
            modifier = Modifier
                .padding(horizontal = 46.dp),
        )

        (cardState as? MatchingCardState.HasRecord)?.let { record ->
            RecordSection(
                winCount = record.winCount,
                loseCount = record.loseCount,
                reviewCount = record.reviewCount,
            )
        }
    }
}

@Composable
private fun RecordSection(
    winCount: Int,
    loseCount: Int,
    reviewCount: Long,
) {
    CardDescription(
        prefixText = stringResource(record),
        suffixText = stringResource(win_lose_count, winCount, loseCount),
        modifier = Modifier.padding(top = 8.dp),
    )

    CardDescription(
        prefixText = stringResource(review),
        suffixText = stringResource(count, reviewCount),
    )
}


@Composable
private fun UserInfo(
    nickname: String,
    @DrawableRes genderIcon: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = nickname,
            style = SmashingTheme.typography.sm.semibold14,
            color = SmashingTheme.colors.txtPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Icon(
            imageVector = ImageVector.vectorResource(genderIcon),
            contentDescription = null,
            tint = SmashingTheme.colors.iconPrimary,
        )
    }
}

@Composable
private fun CardDescription(
    prefixText: String,
    suffixText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = prefixText,
            style = SmashingTheme.typography.xs.medium12,
            color = SmashingTheme.colors.txtTertiary,
        )

        Text(
            text = suffixText,
            style = SmashingTheme.typography.sm.medium14,
            color = SmashingTheme.colors.txtSecondary,
        )
    }
}
