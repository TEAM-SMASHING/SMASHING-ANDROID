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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.profile
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.mapper.img
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.style.toTierInfoStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.type.SportType
import com.smashing.app.presentation.profile.component.ProfileStatsBar
import com.smashing.app.presentation.profile.component.ProfileTierBox
import com.smashing.app.presentation.profile.component.ReviewCard
import com.smashing.app.presentation.profile.component.UserProfileCard


@Composable
fun MyProfileRoute(
    navigateToSportAdd: () -> Unit,
    navigateToTierInfo: (TierInfoStyle, SportType) -> Unit,
    navigateToReview: (String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchProfileInfo()
        viewModel.fetchReviews()
        viewModel.fetchMyRecentReviewStats()

    }

    MyProfileScreen(
        modifier = modifier,
        uiState = uiState,
        onSportClick = viewModel::selectProfileId,
        onAddSportClick = navigateToSportAdd,
        navigateToTierInfo = {
            navigateToTierInfo(
                uiState.profileInfo.tierType.toTierInfoStyle(),
                uiState.profileInfo.sportType
            )
        },
        onReviewClick = navigateToReview,
    )
}

@Composable
private fun MyProfileScreen(
    uiState: MyProfileContract.State,
    onAddSportClick: () -> Unit,
    navigateToTierInfo: () -> Unit,
    onReviewClick: (String?) -> Unit,
    onSportClick: (String) -> Unit,
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
            title = stringResource(profile),
            topBarType = TopBarType.DEFAULT,
            onClick = null,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        ) {
            UserProfileCard(
                nickname = uiState.profileInfo.nickname,
                gender = uiState.profileInfo.genderType,
                tierType = uiState.profileInfo.tierType,
                winCount = uiState.profileInfo.winCount,
                loseCount = uiState.profileInfo.loseCount,
                reviewCount = uiState.profileInfo.reviewCount,
            )

            ProfileTierBox(
                tierType = uiState.profileInfo.tierType,
                sportProfileList = uiState.sportProfileList,
                selectedProfileId = uiState.selectedSportProfileId,
                onSportClick = onSportClick,
                tierIconResId = uiState.profileInfo.tierType.img(),
                progress = ((uiState.profileInfo.lp - uiState.profileInfo.minLp).toFloat() / (uiState.profileInfo.maxLp - uiState.profileInfo.minLp).toFloat()),
                lpStatus = (uiState.profileInfo.maxLp - uiState.profileInfo.lp) + 1,
                totalLp = (uiState.profileInfo.maxLp) + 1,
                onAddSportClick = if (isMaxProfileReached) null else onAddSportClick,
                onTierInfoClick = navigateToTierInfo,
            )

            ProfileStatsBar(
                winCount = uiState.profileInfo.winCount,
                loseCount = uiState.profileInfo.loseCount,
            )

            ReviewCard(
                reviews = uiState.gameReview,
                onViewAllReviewClick = { onReviewClick(null) },
                bestCount = uiState.gameReviewResult.bestCount,
                goodCount = uiState.gameReviewResult.goodCount,
                badCount = uiState.gameReviewResult.badCount,
            )

            Spacer(Modifier.height(16.dp))
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
        )
    }
}
