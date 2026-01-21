package com.smashing.app.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.component.badge.TierBadge
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.mapper.icon20
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

@Composable
fun UserProfileCard(
    tierType: TierType,
    nickname: String,
    gender: GenderType,
    winCount: Int,
    loseCount: Int,
    reviewCount: Long,
    modifier: Modifier = Modifier,
    onCompeteClick: (() -> Unit)? = null
) {

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
                placeholderDrawable = ProfileImageProvider.getTempImg(nickname),
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
                        painter = painterResource(id = gender.icon20()),
                        contentDescription = null,
                        tint = SmashingTheme.colors.iconPrimary,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TierBadge(
                    tierType = tierType,
                )
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

        if (onCompeteClick != null) {
            Spacer(modifier = Modifier.height(16.dp))

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = "경쟁 신청하기",
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(),
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
