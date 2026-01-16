package com.smashing.app.presentation.ranking.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_crown
import com.smashing.app.R.drawable.ic_rank_first
import com.smashing.app.R.drawable.ic_rank_second
import com.smashing.app.R.drawable.ic_rank_third
import com.smashing.app.R.drawable.img_tier_dummy
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.ranking.type.RankerType
import com.smashing.app.presentation.ranking.type.RankerType.FIRST
import com.smashing.app.presentation.ranking.type.RankerType.SECOND
import com.smashing.app.presentation.ranking.type.RankerType.THIRD
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Composable
fun Ranker(
    rankerList: ImmutableList<UserRank>?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        RankerItem(
            userRank = rankerList?.getOrNull(0),
            rankerType = FIRST,
            modifier = Modifier
                .align(Alignment.BottomCenter),
        )
        RankerItem(
            userRank = rankerList?.getOrNull(1),
            rankerType = SECOND,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.BottomStart),
        )
        RankerItem(
            userRank = rankerList?.getOrNull(2),
            rankerType = THIRD,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.BottomEnd),
        )
    }
}

@Composable
private fun RankerItem(
    userRank: UserRank?,
    rankerType: RankerType,
    modifier: Modifier = Modifier,
) {
    val backgroundGradation = Brush.verticalGradient(
        colors = listOf(
            colors.bgOverlay,
            colors.bgCanvas,
        )
    )


    Column(
        modifier = modifier
            .width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val paddingValues = if (FIRST == rankerType) {
            PaddingValues(
                start = 25.dp,
                end = 25.dp,
                bottom = 11.dp,
            )
        } else {
            PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 9.dp,
            )
        }

        if (userRank != null && rankerType == FIRST) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_crown),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }

        if (userRank != null) {
            UrlImage(
                url = ProfileImageProvider.getTempUrl(userRank.userId),
                modifier = Modifier
                    .height(40.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )

            Text(
                text = userRank.nickname,
                style = typography.sm.medium14,
                color = colors.txtPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(
                        brush = backgroundGradation,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                        ),
                    )
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(
                        when (rankerType) {
                            FIRST -> ic_rank_first
                            SECOND -> ic_rank_second
                            THIRD -> ic_rank_third
                        }
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )

                Spacer(modifier = Modifier.height(8.dp))


                //TODO 티어 뱃지 이미지 완료 후 변경 예정
                if (userRank != null) {
                    Image(
                        painter = painterResource(id = img_tier_dummy),
                        contentDescription = null,
                        modifier = Modifier
                            .height(if (rankerType == FIRST) 60.dp else 40.dp)
                            .aspectRatio(1f),
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    RankLp(lp = userRank.lp)
                } else {
                    Spacer(
                        modifier = Modifier
                            .size(
                                height = if (rankerType == FIRST) 78.dp else 58.dp,
                                width = 67.dp,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun RankLp(
    lp: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "$lp LP",
        style = typography.xxs.regular10,
        color = colors.txtTertiary,
        textAlign = TextAlign.Center,
        modifier = modifier
            .width(67.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun RankerItemPreview_FirstPlace() {
    RankerItem(
        userRank = UserRank(
            userId = "user1",
            nickname = "1위 유저",
            rank = 1,
            tierType = TierType.CHALLENGER,
            lp = 2500,
        ),
        rankerType = FIRST,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun RankerItemPreview_SecondPlace() {
    RankerItem(
        userRank = UserRank(
            userId = "user2",
            nickname = "2위 유저",
            rank = 2,
            tierType = TierType.DIAMOND_1,
            lp = 2300,
        ),
        rankerType = SECOND,
        modifier = Modifier.padding(horizontal = 8.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun RankerItemPreview_EmptyPlace() {
    RankerItem(
        userRank = null,
        rankerType = SECOND,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun RankerPreview_AllThree() {
    Ranker(
        rankerList = listOf(
            UserRank(
                userId = "user1",
                nickname = "1위 유저",
                rank = 1,
                tierType = TierType.CHALLENGER,
                lp = 2500,
            ),
            UserRank(
                userId = "user2",
                nickname = "2위 유저",
                rank = 2,
                tierType = TierType.CHALLENGER,
                lp = 2450,
            ),
            UserRank(
                userId = "user3",
                nickname = "3위 유저",
                rank = 3,
                tierType = TierType.CHALLENGER,
                lp = 2400,
            ),
        ).toImmutableList(),
        modifier = Modifier
            .background(
                color = colors.bgCanvas,
            )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun RankerPreview_FirstAndSecond() {
    Ranker(
        rankerList = listOf(
            UserRank(
                userId = "user1",
                nickname = "1위 유저",
                rank = 1,
                tierType = TierType.CHALLENGER,
                lp = 2500,
            ),
            UserRank(
                userId = "user2",
                nickname = "2위 유저",
                rank = 2,
                tierType = TierType.CHALLENGER,
                lp = 2450,
            ),
        ).toImmutableList(),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun RankerPreview_Empty() {
    Ranker(
        rankerList = null,
    )
}