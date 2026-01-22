package com.smashing.app.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.string.lose_label
import com.smashing.app.R.string.profile_rate_percent
import com.smashing.app.R.string.rate_label
import com.smashing.app.R.string.win_label
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme


@Composable
fun ProfileStatsBar(
    winCount: Int,
    loseCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(
                intrinsicSize = IntrinsicSize.Max,
            )
            .clip(RoundedCornerShape(8.dp))
            .background(SmashingTheme.colors.bgSurface)
            .padding(
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileStatItem(
            count = "$winCount",
            label = stringResource(id = win_label),
            countColor = SmashingTheme.colors.txtEmphasis,
            modifier = Modifier.weight(1f),
        )

        StatDivider()

        ProfileStatItem(
            count = "$loseCount",
            label = stringResource(id = lose_label),
            countColor = SmashingTheme.colors.txtRed,
            modifier = Modifier.weight(1f),
        )

        StatDivider()

        ProfileStatItem(
            count = stringResource(profile_rate_percent, winRate(winCount, loseCount)),
            label = stringResource(id = rate_label),
            countColor = SmashingTheme.colors.txtPrimary,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ProfileStatItem(
    count: String,
    label: String,
    countColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = count,
            color = countColor,
            style = SmashingTheme.typography.xl.semibold20,
        )
        Text(
            text = label,
            color = SmashingTheme.colors.txtSecondary,
            style = SmashingTheme.typography.xs.medium12,
        )
    }
}

@Composable
private fun StatDivider(
    modifier: Modifier = Modifier,
) {
    VerticalDivider(
        modifier = modifier.width(1.dp),
        color = SmashingTheme.colors.borderSecondary,
    )
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

@Preview
@Composable
private fun ProfileStatsBarPreview() {
    SmashingAndroidTheme {
        Column {
            ProfileStatsBar(
                winCount = 12,
                loseCount = 5,
            )
        }
    }
}
