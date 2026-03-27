package com.smashing.app.core.designsystem.component.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_bronze
import com.smashing.app.R.drawable.ic_gold
import com.smashing.app.R.drawable.ic_silver
import com.smashing.app.R.drawable.img_profile
import com.smashing.app.R.string.ranking_tier_with_lp
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.mapper.img
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.type.TierType

/**
 * 랭킹 아이템 컴포넌트
 * 랭킹 목록에서 사용자 정보를 표시하는 컴포넌트입니다.
 * 순위에 따라 금/은/동 메달 아이콘 또는 숫자를 표시하며, 사용자 프로필 이미지, 닉네임, 티어, LP를 표시합니다.
 * @param userProfileId 사용자 식별자
 * @param nickname 사용자 닉네임
 * @param rank 순위 (1위는 금메달, 2위는 은메달, 3위는 동메달, 그 외는 숫자로 표시)
 * @param tier 사용자의 티어 타입
 * @param lp 사용자의 LP (League Points)
 * @param onClick 아이템 클릭 시 호출되는 콜백
 * @param modifier 적용할 Modifier
 */

@Composable
fun SmashingRankingItem(
    userProfileId: String,
    nickname: String,
    rank: Int,
    tier: TierType,
    lp: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(
                onClick = onClick,
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (rank) {
            1 -> Icon(
                imageVector = ImageVector.vectorResource(ic_gold),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            2 -> Icon(
                imageVector = ImageVector.vectorResource(ic_silver),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            3 -> Icon(
                imageVector = ImageVector.vectorResource(ic_bronze),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            else -> Text(
                text = rank.toString(),
                style = typography.sm.medium14,
                color = colors.txtPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(24.dp),
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        UrlImage(
            placeholderDrawable = img_profile,
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = nickname,
                style = typography.sm.medium14,
                color = colors.txtPrimary,
            )
            Text(
                text = stringResource(
                    ranking_tier_with_lp,
                    tier.tierName,
                    lp,
                ),
                style = typography.xs.regular12,
                color = colors.txtTertiary,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        //TODO 티어 뱃지 TierType 사용해 이미지 수정 예정
        Image(
            painter = painterResource(id = tier.img()),
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmashingRankingItemPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SmashingRankingItem(
                userProfileId = "user1",
                nickname = "플레이어1",
                rank = 1,
                tier = TierType.CHALLENGER,
                lp = 2450,
                onClick = { },
            )
            SmashingRankingItem(
                userProfileId = "user2",
                nickname = "플레이어2",
                rank = 2,
                tier = TierType.DIAMOND_1,
                lp = 2350,
                onClick = { },
            )
            SmashingRankingItem(
                userProfileId = "user3",
                nickname = "플레이어3",
                rank = 3,
                tier = TierType.PLATINUM_2,
                lp = 2250,
                onClick = { },
            )
            SmashingRankingItem(
                userProfileId = "user4",
                nickname = "플레이어4",
                rank = 4,
                tier = TierType.GOLD_1,
                lp = 1850,
                onClick = { },
            )
        }
    }
}
