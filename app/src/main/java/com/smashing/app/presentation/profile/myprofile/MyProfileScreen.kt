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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R.string.profile
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.mapper.img
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.profile.component.ProfileStatsBar
import com.smashing.app.presentation.profile.component.ProfileTierBox
import com.smashing.app.presentation.profile.component.ReviewCard
import com.smashing.app.presentation.profile.component.UserProfileCard
import com.smashing.app.presentation.profile.myprofile.MyProfileContract.SideEffect


@Composable
fun MyProfileRoute(
    navigateToSportAdd: () -> Unit,
    navigateToTierGuide: () -> Unit,
    navigateToReview: (String?) -> Unit,
    updateBottomBar: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchProfileInfo()
        viewModel.fetchReviews()

    }

    MyProfileScreen(
        modifier = modifier,
        uiState = uiState,
        updateBottomBar = updateBottomBar,
        onSportClick = viewModel::selectProfileId,
        onAddSportClick = navigateToSportAdd,
        onTierGuideClick = navigateToTierGuide,
        onReviewClick = { userId ->
            navigateToReview(userId) },
    )
}

@Composable
private fun MyProfileScreen(
    uiState: MyProfileContract.State,
    onAddSportClick: () -> Unit,
    onTierGuideClick: () -> Unit,
    onReviewClick: (String?) -> Unit,
    onSportClick: (String) -> Unit,
    updateBottomBar: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    val isMaxProfileReached = uiState.sportProfileList.size >= 3
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
                .nestedScroll(nestedScrollConnection)
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
                progress = uiState.profileInfo.lp.toFloat() / uiState.profileInfo.maxLp,
                lpStatus = uiState.profileInfo.minLp,
                totalLp = uiState.profileInfo.maxLp,
                onAddSportClick = if (isMaxProfileReached) null else onAddSportClick,
                onTierInfoClick = onTierGuideClick,
            )

            ProfileStatsBar(
                winCount = uiState.profileInfo.winCount,
                loseCount = uiState.profileInfo.loseCount,
            )

            ReviewCard(
                reviews = uiState.gameReview,
                onViewAllReviewClick = onReviewClick,
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
            onTierGuideClick = {},
            onReviewClick = {},
            onSportClick = {},
            updateBottomBar = {},
        )
    }
}
