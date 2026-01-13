package com.smashing.app.presentation.submit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.util.ProfileImageProvider

private const val SCORE_VERTICAL_RATIO = 14 / 33f

@Composable
fun SubmitScoreCard(
    submitterUserId: String,
    submitterName: String,
    submitterScore: Int,
    receiverUserId: String,
    receiverName: String,
    receiverScore: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 28.dp)
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                vertical = 36.dp,
            )
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ProfileInfo(
            userId = submitterUserId,
            userName = submitterName,
            modifier = Modifier.padding(start = 36.dp),
        )

        ScoreSection(
            submitterScore = submitterScore,
            receiverScore = receiverScore,
        )

        ProfileInfo(
            userId = receiverUserId,
            userName = receiverName,
            modifier = Modifier.padding(end = 36.dp),
        )
    }
}

@Composable
private fun ProfileInfo(
    userId: String,
    userName: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UrlImage(
            url = ProfileImageProvider.getTempUrl(userId),
            modifier = Modifier
                .height(52.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = userName,
            color = SmashingTheme.colors.txtMuted,
            style = SmashingTheme.typography.sm.medium14,
        )
    }
}

@Composable
private fun ScoreSection(
    submitterScore: Int,
    receiverScore: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(SCORE_VERTICAL_RATIO))

        Text(
            text = stringResource(
                R.string.score_format,
                submitterScore,
                receiverScore
            ),
            color = SmashingTheme.colors.txtSecondary,
            style = SmashingTheme.typography.hero.semibold28,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
