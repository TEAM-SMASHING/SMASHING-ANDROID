package com.smashing.app.presentation.profile.user.copmponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.string.record_label
import com.smashing.app.R.string.review
import com.smashing.app.R.drawable.ic_man_20
import com.smashing.app.R.drawable.ic_woman_20
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.designsystem.component.badge.TierBadge
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.util.ProfileImageProvider

@Composable
fun UserProfileCard(
    tierType: TierType,
    nickname: String,
    gender: GenderType,
    winCount: Int,
    loseCount: Int,
    reviewCount: Int,
    modifier: Modifier = Modifier,
) {
    val genderIconRes = when (gender) {
        GenderType.MALE -> ic_man_20
        GenderType.FEMALE -> ic_woman_20
    }

    val genderIconColor = SmashingTheme.colors.iconPrimary

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SmashingTheme.colors.bgSurface)
            .padding(vertical = 20.dp, horizontal = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UrlImage(
                url = ProfileImageProvider.getTempUrl(nickname),
                modifier = Modifier
                    .height(60.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = nickname,
                        style = SmashingTheme.typography.lg.semibold18,
                        color = SmashingTheme.colors.txtPrimary,
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        painter = painterResource(id = genderIconRes),
                        contentDescription = null,
                        tint = genderIconColor,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TierBadge(
                        tierType=tierType,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ProfileStatRow(
                label = stringResource(id = record_label),
                value = "${winCount}승 ${loseCount}패",
            )
            ProfileStatRow(
                label = stringResource(review),
                value = "$reviewCount",
            )
        }
    }
}

@Composable
private fun ProfileStatRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = SmashingTheme.typography.md.medium16,
            color = SmashingTheme.colors.txtTertiary,
        )
        Text(
            text = value,
            style = SmashingTheme.typography.md.semibold16,
            color = SmashingTheme.colors.txtSecondary,
        )
    }
}


@Preview
@Composable
private fun UserProfileCardPreview() {
    SmashingAndroidTheme {
        UserProfileCard(
            nickname = "하나둘셋넷다여칠팔구",
            gender = GenderType.FEMALE,
            tierType = TierType.GOLD_1,
            winCount = 254,
            loseCount = 38,
            reviewCount = 32,
        )
    }
}
