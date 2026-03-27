package com.smashing.app.presentation.profile.myprofile

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.profile
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.mapper.img
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.core.designsystem.state.TopBarState
import com.smashing.app.core.designsystem.style.toTierInfoStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.type.SportType
import com.smashing.app.presentation.profile.component.ProfileStatsBar
import com.smashing.app.presentation.profile.component.ProfileTierBox
import com.smashing.app.presentation.profile.component.ReviewCard
import com.smashing.app.presentation.profile.component.UserProfileCard
import kotlinx.collections.immutable.toImmutableList


@Composable
fun MyProfileRoute(
    navigateUp: () -> Unit,
    navigateToSportAdd: () -> Unit,
    navigateToTierInfo: (TierInfoStyle, SportType) -> Unit,
    navigateToReview: (String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchProfileInfo()
        viewModel.fetchMyProfileReviewList()
        viewModel.fetchMyRecentReviewStats()

    }

    MyProfileScreen(
        modifier = modifier,
        uiState = uiState,
        onSportClick = viewModel::selectProfileId,
        onAddSportClick = navigateToSportAdd,
        navigateToTierInfo = {
            navigateToTierInfo(
                uiState.activeProfile.tierType.toTierInfoStyle(),
                uiState.activeProfile.sportType
            )
        },
        onReviewClick = navigateToReview,
        onBackClick = navigateUp,
    )
}

@Composable
private fun MyProfileScreen(
    uiState: MyProfileContract.State,
    onAddSportClick: () -> Unit,
    navigateToTierInfo: () -> Unit,
    onReviewClick: (String?) -> Unit,
    onSportClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    val isMaxProfileReached = uiState.sportProfileList.size >= 3

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .systemBarsPadding(),
    ) {

        SmashingDefaultTopBar(
            state = TopBarState.Back(
                title = stringResource(profile),
                onBackClick = onBackClick,
            ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        ) {
            UserProfileCard(
                nickname = uiState.myProfileInfo.nickname,
                gender = uiState.myProfileInfo.genderType,
                tierType = uiState.activeProfile.tierType,
                winCount = uiState.activeProfile.winCount,
                loseCount = uiState.activeProfile.loseCount,
                reviewCount = uiState.myProfileInfo.reviewCount,
            )

            ProfileTierBox(
                tierType = uiState.activeProfile.tierType,
                sportProfileList = uiState.sportProfileList.toImmutableList(),
                selectedProfileId = uiState.selectedSportProfileId,
                onSportClick = onSportClick,
                tierIconResId = uiState.myProfileInfo.profileInfo.tierType.img(),
                progress = ((uiState.activeProfile.lp - uiState.activeProfile.minLp).toFloat() /
                        (uiState.activeProfile.maxLp - uiState.activeProfile.minLp).toFloat()),
                lpStatus = (uiState.activeProfile.maxLp - uiState.activeProfile.lp) + 1,
                totalLp = (uiState.activeProfile.maxLp) + 1,
                onAddSportClick = if (isMaxProfileReached) null else onAddSportClick,
                onTierInfoClick = navigateToTierInfo,
            )
            ProfileStatsBar(
                winCount = uiState.activeProfile.winCount,
                loseCount = uiState.activeProfile.loseCount,
            )

            ReviewCard(
                reviews = uiState.gameReview,
                onViewAllReviewClick = { onReviewClick(null) },
                bestCount = uiState.gameReviewResult.bestCount,
                goodCount = uiState.gameReviewResult.goodCount,
                badCount = uiState.gameReviewResult.badCount,
            )
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    SmashingAndroidTheme {
        MyProfileScreen(
            uiState = MyProfileContract.State(),
            onAddSportClick = {},
            navigateToTierInfo = {},
            onReviewClick = {},
            onSportClick = {},
            onBackClick = {},
        )
    }
}
