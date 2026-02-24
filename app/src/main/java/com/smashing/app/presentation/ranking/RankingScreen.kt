package com.smashing.app.presentation.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.ranking_tier_with_lp
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.component.ranking.SmashingRankingItem
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.mapper.img
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.presentation.ranking.component.Ranker
import kotlinx.collections.immutable.toImmutableList
import com.smashing.app.core.designsystem.theme.primary300
import com.smashing.app.core.extension.dropShadow
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.type.TierType

@Composable
fun RankingRoute(
    navigateUp: () -> Unit,
    navigateToProfile: (String) -> Unit,
    navigateToMyProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RankingScreen(
        uiState = uiState,
        navigateUp = navigateUp,
        navigateToProfile = navigateToProfile,
        navigateToMyProfile = navigateToMyProfile,
        modifier = modifier,
    )
}

@Composable
private fun RankingScreen(
    uiState: RankingContract.State,
    navigateUp: () -> Unit,
    navigateToProfile: (String) -> Unit,
    navigateToMyProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val density = LocalDensity.current
    var myRankingHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colors.bgCanvas,
            )
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .dropShadow(
                    shape = CircleShape,
                    color = primary300,
                    spread = 100.dp,
                    blur = 100.dp,
                    offsetX = -50.dp,
                    offsetY = 190.dp,
                )
                .size(200.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SmashingDefaultTopBar(
                title = "전체 랭킹",
                topBarType = TopBarType.BACK,
                onClick = navigateUp,
            )

            Ranker(
                rankerList = uiState.topRankingList,
                myUserId = uiState.userInfo?.userId,
                navigateToProfile = navigateToProfile,
                navigateToMyProfile = navigateToMyProfile,
            )

            if (uiState.restRankingList.isNotEmpty()) {
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
                        bottom = myRankingHeight + 12.dp,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = uiState.restRankingList,
                    ) { user ->
                        SmashingRankingItem(
                            userId = user.userId,
                            nickname = user.nickname,
                            rank = user.rank,
                            tier = user.tier,
                            lp = user.lp,
                            onClick = {
                                if (user.userId != uiState.userInfo?.userId) {
                                    navigateToProfile(user.userId)
                                } else {
                                    navigateToMyProfile()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(
                            color = colors.bgCanvas
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.weight(0.3f))
                    Text(
                        text = "아직 동네 랭커가 없어요.",
                        style = typography.md.medium16,
                        color = colors.txtTertiary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }


        }
        if (uiState.userInfo != null) {
            MyRanking(
                myRank = uiState.userInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .onGloballyPositioned { coordinates ->
                        myRankingHeight = with(density) {
                            coordinates.size.height.toDp()
                        }
                    }
                    .padding(
                        horizontal = 16.dp,
                    )
                    .padding(
                        bottom = 20.dp
                    )
                    .navigationBarsPadding()
                    .noRippleClickable(
                        onClick = {}
                    )
            )
        }
    }
}

@Composable
private fun MyRanking(
    myRank: UserRank,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = colors.bgSurface,
            )
            .border(
                width = 1.dp,
                color = colors.borderTertiary,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                top = 15.dp,
                start = 16.dp,
                bottom = 15.dp,
                end = 16.dp,
            )

    ) {
        UrlImage(
            placeholderDrawable = ProfileImageProvider.getTempImg(myRank.nickname),
            modifier = Modifier
                .size(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = myRank.nickname,
                style = typography.sm.medium14,
                color = colors.txtPrimary,
            )
            Text(
                text = stringResource(
                    ranking_tier_with_lp,
                    myRank.tier.tierName,
                    myRank.lp,
                ),
                style = typography.xs.regular12,
                color = colors.txtTertiary,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = myRank.tier.img()),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .aspectRatio(1f)
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun RankingScreenPreview_OnlyFirst() {
    RankingScreen(
        uiState = RankingContract.State(
            totalRankingList = listOf(
                UserRank(
                    userId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
            ).toImmutableList(),
            topRankingList = listOf(
                UserRank(
                    userId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
            ).toImmutableList(),
        ),
        navigateUp = {},
        navigateToProfile = {},
        navigateToMyProfile = {},
    )
}

@Composable
@Preview(backgroundColor = 0xFF000000, showBackground = true)
fun RankingScreenPreview() {
    RankingScreen(
        uiState = RankingContract.State(
            totalRankingList = listOf(
                UserRank(
                    rank = 1,
                    userId = "user1",
                    nickname = "1위 유저",
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
            ).toImmutableList(),
            topRankingList = listOf(
                UserRank(
                    rank = 1,
                    userId = "user1",
                    nickname = "1위 유저",
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
            ).toImmutableList(),
        ),
        navigateUp = {},
        navigateToProfile = {},
        navigateToMyProfile = {},
    )
}