package com.smashing.app.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.string.profile
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.profile.component.ProfileReviewCard
import com.smashing.app.presentation.profile.user.copmponent.ProfileStatsBar
import com.smashing.app.presentation.profile.user.copmponent.ProfileTierBox
import com.smashing.app.presentation.profile.user.copmponent.UserProfileCard


@Composable
fun ProfileRoute(
    navigateToSportAdd: () -> Unit,
    navigateToTierGuide: () -> Unit,
    navigateToReviews: () -> Unit,
    updateBottomBar: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        modifier = modifier,
        uiState = uiState,
        updateBottomBar = updateBottomBar,
        onSportClick = viewModel::updateSelectedSport,
        onAddSportClick = navigateToSportAdd,
        onTierGuideClick = navigateToTierGuide,
        onReviewsClick = navigateToReviews,
    )
}

@Composable
private fun ProfileScreen(
    uiState: ProfileContract.State,
    onAddSportClick: () -> Unit,
    onTierGuideClick: () -> Unit,
    onReviewsClick: () -> Unit,
    onSportClick: (SportType) -> Unit,
    updateBottomBar: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < -10) {
                    updateBottomBar(false)
                } else if (available.y > 10) {
                    updateBottomBar(true)
                }
                return Offset.Zero
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .systemBarsPadding(),
    ) {

        SmashingDefaultTopBar(
            title = stringResource(profile),
            topBarType = TopBarType.DEFAULT,
            onClick = null,
        )

        LazyColumn(
            state = lazyListState,
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {

            //TODO 하드코딩된 값 변경 필요
            item {
                UserProfileCard(
                    nickname = "하나둘셋넷다여칠팔구",
                    gender = GenderType.FEMALE,
                    tierType = TierType.GOLD_1,
                    winCount = 254,
                    loseCount = 38,
                    reviewCount = 32,
                )
            }

            item {
                ProfileTierBox(
                    tierType = uiState.profileInfo.tierType,
                    sports = uiState.profileInfo.mySports,
                    selectedSport = uiState.profileInfo.selectedSport,
                    onSportClick = onSportClick,
                    tierIconResId = R.drawable.ic_check, // TODO 수정 예정
                    progress = uiState.profileInfo.lpProgress,
                    lpStatus = uiState.profileInfo.minLp,
                    totalLp = uiState.profileInfo.maxLp,
                    onAddSportClick = onAddSportClick,
                    onTierInfoClick = onTierGuideClick,
                )
            }
            item {
                ProfileStatsBar(
                    winCount = uiState.profileInfo.winCount,
                    loseCount = uiState.profileInfo.loseCount,
                )
            }
            item {
                ProfileReviewCard(
                    reviews = uiState.reviews,
                    onViewAllReviewClick = onReviewsClick,
                    excellentCount = uiState.reviewRate.best,
                    goodCount = uiState.reviewRate.good,
                    badCount = uiState.reviewRate.bad,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    SmashingAndroidTheme {
        ProfileScreen(
            uiState = ProfileContract.State(),
            onAddSportClick = {},
            onTierGuideClick = {},
            onReviewsClick = {},
            onSportClick = {},
            updateBottomBar = {},
        )
    }
}
