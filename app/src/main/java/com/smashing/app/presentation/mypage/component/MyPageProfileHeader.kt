package com.smashing.app.presentation.mypage.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.component.badge.TierBadge
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.data.type.TierType
import com.smashing.app.R.string.mypage_my_profile

@Composable
fun MyPageProfileHeader(
    tierType: TierType,
    nickname: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        UrlImage(
            placeholderDrawable = ProfileImageProvider.getTempImg(nickname),
            modifier = Modifier
                .height(60.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            Text(
                text = nickname,
                style = SmashingTheme.typography.md.medium16,
                color = SmashingTheme.colors.txtPrimary,
            )

            Spacer(modifier = Modifier.height(4.dp))

            TierBadge(
                tierType = tierType,
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 20.dp))
                .border(
                    width = 1.dp,
                    color = SmashingTheme.colors.borderTertiary,
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .padding(vertical = 4.dp, horizontal = 9.dp)
                .noRippleClickable(
                    onClick = onClick,
                )
        ) {
            Text(
                text = stringResource(mypage_my_profile),
                style = SmashingTheme.typography.xs.medium12,
                color = SmashingTheme.colors.txtPrimary,
            )

        }
    }
}


@Preview
@Composable
private fun MyPageProfileHeaderPreview() {
    SmashingAndroidTheme {
        MyPageProfileHeader(
            nickname = "하나둘셋넷다여칠팔구",
            tierType = TierType.GOLD_1,
            onClick = {},
        )
    }
}
