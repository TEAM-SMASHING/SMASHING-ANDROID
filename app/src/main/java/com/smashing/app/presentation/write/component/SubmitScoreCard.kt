package com.smashing.app.presentation.write.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_crown
import com.smashing.app.R.string.score_format
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.presentation.write.model.MatchPlayer

@Composable
fun SubmitScoreCard(
    submitter: MatchPlayer,
    submitterScore: Int,
    receiver: MatchPlayer,
    receiverScore: Int,
    modifier: Modifier = Modifier,
    winner: MatchPlayer? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(12.dp),
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ProfileInfo(
            player = submitter,
            isWinner = winner?.userId == submitter.userId,
            modifier = Modifier
                .padding(start = 36.dp),
        )

        Text(
            text = stringResource(
                score_format,
                submitterScore,
                receiverScore
            ),
            color = SmashingTheme.colors.txtSecondary,
            style = SmashingTheme.typography.hero.semibold28,
            modifier = Modifier
                .padding(
                    top = 50.dp,
                    bottom = 68.dp,
                )
        )

        ProfileInfo(
            player = receiver,
            isWinner = winner?.userId == receiver.userId,
            modifier = Modifier
                .padding(end = 36.dp),
        )
    }
}

@Composable
private fun ProfileInfo(
    player: MatchPlayer,
    isWinner: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {
            UrlImage(
                url = ProfileImageProvider.getTempUrl(player.userId),
                modifier = Modifier
                    .height(64.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )

            if (isWinner) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_crown),
                    contentDescription = null,
                    tint = SmashingTheme.colors.iconCrown,
                    modifier = Modifier
                        .offset(y = (-24).dp),
                )
            }
        }

        Spacer(Modifier.height(5.dp))

        Text(
            text = player.name,
            color = SmashingTheme.colors.txtMuted,
            style = SmashingTheme.typography.sm.medium14,
        )
    }
}
