package com.smashing.app.presentation.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.img_tier_dummy
import com.smashing.app.R.string.ranking_tier_with_lp
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.component.ranking.SmashingRankingItem
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.presentation.ranking.component.Ranker
import kotlinx.collections.immutable.toImmutableList
import com.smashing.app.core.designsystem.theme.primary300
import com.smashing.app.core.extension.dropShadow
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.type.TierType

@Composable
fun RankingRoute(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RankingScreen(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun RankingScreen(
    uiState: RankingContract.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .fillMaxSize()
            .systemBarsPadding()
            .background(
                color = colors.bgCanvas,
            )
    ) {
        Box(
            modifier = Modifier
                .dropShadow(
                    shape = CircleShape,
                    color = primary300,
                    spread = 100.dp,
                    blur = 100.dp,
                    offsetX = 50.dp,
                    offsetY = 160.dp,
                )
                .size(200.dp)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SmashingDefaultTopBar(
            title = "전체 랭킹",
            topBarType = TopBarType.BACK,
            onClick = {},
        )

        val rankingList = uiState.rankingList

        val topThree = rankingList
            .filter { it.rank in 1..3 }
            .sortedBy { it.rank }
            .take(3)
            .toImmutableList()

        val rest = rankingList
            .filter { it.rank > 3 }
            .sortedBy { it.rank }
            .toImmutableList()

        Ranker(
            rankerList = topThree,
        )

        if (rest.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        color = colors.bgCanvas,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                        )
                    )
                    .padding(
                        top = 44.dp,
                    ),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = rest,
                    key = { it.userId },
                ) { user ->
                    SmashingRankingItem(
                        userId = user.userId,
                        nickname = user.nickname,
                        rank = user.rank,
                        tier = user.tierType,
                        lp = user.lp,
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }
        } else {
            Text(
                text = "아직 동네 랭커가 없어요.",
                style = typography.md.medium16,
                color = colors.txtTertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        color = colors.bgCanvas
                    )
            )
        }

        MyRanking(
            userId = "myUserId",
            nickname = "내 닉네임",
            tier = TierType.GOLD_1,
            lp = 1850,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun MyRanking(
    userId: String,
    nickname: String,
    tier: TierType,
    lp: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = colors.bgOverlay,
            )
            .padding(
                top = 15.dp,
                bottom = 33.dp,
                start = 16.dp,
                end = 16.dp,
            )
    ) {
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
            painter = painterResource(id = img_tier_dummy),
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f)
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun RankingScreenPreview_OnlyFirst() {
    RankingScreen(
        uiState = RankingContract.State(
            rankingList = listOf(
                UserRank(
                    userId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tierType = TierType.CHALLENGER,
                    lp = 2500,
                ),
            ).toImmutableList()
        ),
    )
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun RankingScreenPreview_TopTen() {
    RankingScreen(
        uiState = RankingContract.State(
            rankingList =
                listOf(
                    UserRank("user1", "1위 유저", 1, TierType.CHALLENGER, 2500),
                    UserRank("user2", "2위 유저", 2, TierType.CHALLENGER, 2450),
                    UserRank("user3", "3위 유저", 3, TierType.CHALLENGER, 2400),
                    UserRank("user4", "4위 유저", 4, TierType.DIAMOND_1, 2350),
                    UserRank("user5", "5위 유저", 5, TierType.DIAMOND_1, 2300),
                    UserRank("user6", "6위 유저", 6, TierType.DIAMOND_2, 2250),
                    UserRank("user7", "7위 유저", 7, TierType.DIAMOND_2, 2200),
                    UserRank("user8", "8위 유저", 8, TierType.DIAMOND_3, 2150),
                    UserRank("user9", "9위 유저", 9, TierType.PLATINUM_1, 2100),
                    UserRank("user10", "10위 유저", 10, TierType.PLATINUM_2, 2050),
                ).toImmutableList()

        ),
    )
}


@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun RankingScreenPreview_TopTwenty() {
    RankingScreen(
        uiState = RankingContract.State(
            rankingList =
                listOf(
                    UserRank("user1", "1위 유저", 1, TierType.CHALLENGER, 2500),
                    UserRank("user2", "2위 유저", 2, TierType.CHALLENGER, 2450),
                    UserRank("user3", "3위 유저", 3, TierType.CHALLENGER, 2400),
                    UserRank("user4", "4위 유저", 4, TierType.DIAMOND_1, 2350),
                    UserRank("user5", "5위 유저", 5, TierType.DIAMOND_1, 2300),
                    UserRank("user6", "6위 유저", 6, TierType.DIAMOND_2, 2250),
                    UserRank("user7", "7위 유저", 7, TierType.DIAMOND_2, 2200),
                    UserRank("user8", "8위 유저", 8, TierType.DIAMOND_3, 2150),
                    UserRank("user9", "9위 유저", 9, TierType.PLATINUM_1, 2100),
                    UserRank("user10", "10위 유저", 10, TierType.PLATINUM_2, 2050),
                    UserRank("user11", "11위 유저", 11, TierType.PLATINUM_3, 2000),
                    UserRank("user12", "12위 유저", 12, TierType.GOLD_1, 1950),
                    UserRank("user13", "13위 유저", 13, TierType.GOLD_2, 1900),
                    UserRank("user14", "14위 유저", 14, TierType.GOLD_3, 1850),
                    UserRank("user15", "15위 유저", 15, TierType.SILVER_1, 1800),
                    UserRank("user16", "16위 유저", 16, TierType.SILVER_2, 1750),
                    UserRank("user17", "17위 유저", 17, TierType.SILVER_3, 1700),
                    UserRank("user18", "18위 유저", 18, TierType.BRONZE_1, 1650),
                    UserRank("user19", "19위 유저", 19, TierType.BRONZE_2, 1600),
                    UserRank("user20", "20위 유저", 20, TierType.BRONZE_3, 1550),
                ).toImmutableList()
        )
    )
}