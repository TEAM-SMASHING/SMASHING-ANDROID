package com.smashing.app.core.designsystem.component.ranking

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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.core.util.TierBadgeImageProvider

/**
 * 랭킹 아이템 컴포넌트
 * 랭킹 목록에서 사용자 정보를 표시하는 컴포넌트입니다.
 * 순위에 따라 금/은/동 메달 아이콘 또는 숫자를 표시하며, 사용자 프로필 이미지, 닉네임, 티어, LP를 표시합니다.
 * @param userId 사용자 식별자
 * @param nickname 사용자 닉네임
 * @param rank 순위 (1위는 금메달, 2위는 은메달, 3위는 동메달, 그 외는 숫자로 표시)
 * @param tier 사용자의 티어 타입
 * @param lp 사용자의 LP (League Points)
 * @param onClick 아이템 클릭 시 호출되는 콜백 (userId를 전달)
 * @param modifier 적용할 Modifier
 */

@Composable
fun SmashingRankingItem(
    userId: String,
    nickname: String,
    rank: Int,
    tier: TierType,
    lp: Int,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(
                onClick = { onClick(userId) }
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (rank) {
            1 -> Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_gold),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            2 -> Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_silver),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            3 -> Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_bronze),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            else -> Text(
                text = rank.toString(),
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(24.dp),
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        UrlImage(
            url = ProfileImageProvider.getTempUrl(userId),
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = nickname,
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtPrimary,
            )
            Text(
                text = "${tier.tierName} · $lp",
                style = SmashingTheme.typography.xs.regular12,
                color = SmashingTheme.colors.txtTertiary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        UrlImage(
            url = TierBadgeImageProvider.getTierBadgeUrl(tier),
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmashingRankingItemPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(color = SmashingTheme.colors.bgDimmed),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SmashingRankingItem(
                userId = "user1",
                nickname = "플레이어1",
                rank = 1,
                tier = TierType.CHALLENGER,
                lp = 2450,
                onClick = { userId -> println("User clicked: $userId") },
            )
            SmashingRankingItem(
                userId = "user2",
                nickname = "플레이어2",
                rank = 2,
                tier = TierType.DIAMOND_1,
                lp = 2350,
                onClick = { userId -> println("User clicked: $userId") },
            )
            SmashingRankingItem(
                userId = "user3",
                nickname = "플레이어3",
                rank = 3,
                tier = TierType.PLATINUM_2,
                lp = 2250,
                onClick = { userId -> println("User clicked: $userId") },
            )
            SmashingRankingItem(
                userId = "user4",
                nickname = "플레이어4",
                rank = 4,
                tier = TierType.GOLD_1,
                lp = 1850,
                onClick = { userId -> println("User clicked: $userId") },
            )
        }
    }
}