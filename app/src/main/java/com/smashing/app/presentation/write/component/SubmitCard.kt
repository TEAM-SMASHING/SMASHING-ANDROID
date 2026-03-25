package com.smashing.app.presentation.write.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.smashing.app.R.drawable.ic_crown
import com.smashing.app.R.drawable.img_profile
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.write.model.PlayerInfo

@Composable
fun SubmitCard(
    leftUser: PlayerInfo,
    rightUser: PlayerInfo,
    modifier: Modifier = Modifier,
    winnerId: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                horizontal = 8.dp,
            ),
    ) {
        ProfileInfo(
            player = leftUser,
            isWinner = winnerId == leftUser.userId,
            modifier = Modifier.align(Alignment.CenterStart),
        )

        Text(
            text = "vs",
            color = SmashingTheme.colors.txtSecondary,
            style = SmashingTheme.typography.hero.semibold28,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    top = 50.dp,
                    bottom = 68.dp,
                ),
        )

        ProfileInfo(
            player = rightUser,
            isWinner = winnerId == rightUser.userId,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}

@Composable
private fun ProfileInfo(
    player: PlayerInfo,
    isWinner: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                )
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
            ) {
                UrlImage(
                    placeholderDrawable = img_profile,
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
        }

        Spacer(Modifier.height(5.dp))

        Text(
            text = player.name,
            color = SmashingTheme.colors.txtMuted,
            style = SmashingTheme.typography.sm.medium14,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitCardPreview() {
    SmashingAndroidTheme {
        SubmitCard(
            leftUser = PlayerInfo(userId = "1", name = "하나둘"),
            rightUser = PlayerInfo(userId = "2", name = "하나둘셋넷다여일여아열"),
            winnerId = "1",
        )
    }
}
