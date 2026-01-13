package com.smashing.app.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.ic_fake_red
import com.smashing.app.R.string.profile
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.ProfileReview
import com.smashing.app.data.model.UserProfileInfo
import com.smashing.app.presentation.profile.component.ProfileReviewCard
import com.smashing.app.presentation.profile.component.ProfileStatsBar
import com.smashing.app.presentation.profile.component.ProfileTierBox
import kotlinx.collections.immutable.toImmutableList


@Composable
fun ProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToSportAdd: () -> Unit,
    navigateToTierGuide: () -> Unit,
    navigateToReviews: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        modifier = modifier,
        uiState = uiState,
        onSportClick = {},
        onAddSportClick = navigateToSportAdd,
        onTierGuideClick = navigateToTierGuide,
        onReviewsClick =navigateToReviews,
    )
}

@Composable
private fun ProfileScreen(
    uiState: ProfileContract.State,
    onAddSportClick: () -> Unit,
    onTierGuideClick: () -> Unit,
    onReviewsClick: () -> Unit,
    onSportClick: (SportType) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val navigationBarBottomPadding =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val info = uiState.profileInfo

    if (info == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("loading")
        }
        return
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 20.dp + navigationBarBottomPadding),
    ) {
        item {
            SmashingDefaultTopBar(
                title = stringResource(profile),
                topBarType = TopBarType.DEFAULT,
                onClick = null,
            )
        }
        item {
            ProfileTierBox(
                tierType = uiState.profileInfo.tierType,
                sports = uiState.profileInfo.mySports,

                selectedSport = uiState.profileInfo.selectedSport,
                onSportClick = onSportClick,

                tierIconResId = uiState.profileInfo.tierIconResId,
                progress = uiState.profileInfo.lpProgress,
                lpStatus = uiState.profileInfo.minLp,
                totalLp = uiState.profileInfo.maxLp,
                onTierInfoClick = onTierGuideClick,
                onAddSportClick = onAddSportClick,
            )
        }
        item {
            ProfileStatsBar(
                winCount = uiState.profileInfo.winCount,
                loseCount = uiState.profileInfo.loseCount,
            )
        }
        item {
            Box(modifier = Modifier.padding(16.dp)) {
                ProfileReviewCard(
                    reviews = uiState.reviews,
                    onViewAllClick = onReviewsClick,
                    excellentCount = 12,
                    goodCount = 5,
                    badCount = 3,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    val dummyReviews = listOf(
        ProfileReview(1, "닝우닝", "2일 전", "매너도 좋고, 너무 잘하세요!"),
        ProfileReview(2, "닝우닝닝이", "4일 전", "매너도 좋고, 너무 잘하세요!"),
        ProfileReview(3, "닝우", "5일 전", "매너도 좋고, 너무 잘하세요! 매너도 좋고, 너무 잘하세요! 매너도 좋고, 너무 잘하세요!")
    ).toImmutableList()

    val dummyState = ProfileContract.State(
        profileInfo = UserProfileInfo(
            tierType = TierType.GOLD_1,
            tierIconResId = ic_fake_red,
            mySports = listOf(SportType.PING_PONG, SportType.BADMINTON),
            selectedSport = SportType.PING_PONG,
            lpProgress = 0.1f,
            minLp = 100,
            maxLp = 500,
            winCount = 4,
            loseCount = 5,
        ),
        reviews = dummyReviews
    )


    SmashingAndroidTheme {
        ProfileScreen(
            uiState = dummyState,
            onAddSportClick = {},
            onTierGuideClick = {},
            onReviewsClick = {},
            onSportClick = {},
        )
    }
}

