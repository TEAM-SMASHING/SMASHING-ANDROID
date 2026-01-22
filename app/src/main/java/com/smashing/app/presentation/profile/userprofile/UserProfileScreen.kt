package com.smashing.app.presentation.profile.userprofile

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R.string.profile
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.mapper.img
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.presentation.profile.component.ProfileStatsBar
import com.smashing.app.presentation.profile.component.ProfileTierBox
import com.smashing.app.presentation.profile.component.ReviewCard
import com.smashing.app.presentation.profile.component.UserProfileCard
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.SideEffect.NavigateToAllReview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val BTN_WEIGHT = 131f / 185f

@Composable
fun UserProfileRoute(
    navigateToReview: (String?) -> Unit,
    navigateToSentMatching: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToAllReview -> navigateToReview(sideEffect.userId)
                }
            }
    }

    UserProfileScreen(
        uiState = uiState,
        reviews = uiState.gameReview,
        onYesClick = viewModel::onYesClick,
        onNoClick = viewModel::onNoClick,
        onBackClick = navigateUp,
        onReviewClick = viewModel::navigateToAllReview,
        onCompeteClick = viewModel::requestCompetition,
        onConfirmClick = navigateToSentMatching,
        onDialogDismissClick = viewModel::dismissDialog,
        modifier = modifier,
    )
}

@Composable
private fun UserProfileScreen(
    uiState: UserProfileContract.State,
    reviews: ImmutableList<GameReview>,
    onReviewClick: () -> Unit,
    onBackClick: () -> Unit,
    onYesClick: () -> Unit,
    onNoClick: () -> Unit,
    onCompeteClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bgCanvas)
            .systemBarsPadding(),
    ) {

        SmashingDefaultTopBar(
            title = stringResource(profile),
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 37.dp)
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
                onCompeteClick = onCompeteClick,
                isCompeteEnabled = uiState.isChallengeable,
            )

            ProfileTierBox(
                tierType = uiState.profileInfo.tierType,
                sportProfileList = uiState.sportProfileList,
                selectedProfileId = uiState.selectedSportProfileId,
                tierIconResId = uiState.profileInfo.tierType.img(),
                progress = ((uiState.profileInfo.lp - uiState.profileInfo.minLp).toFloat() / (uiState.profileInfo.maxLp - uiState.profileInfo.minLp).toFloat()),
                lpStatus = (uiState.profileInfo.maxLp - uiState.profileInfo.lp) + 1,
                totalLp = (uiState.profileInfo.maxLp) + 1,
            )

            ProfileStatsBar(
                winCount = uiState.profileInfo.winCount,
                loseCount = uiState.profileInfo.loseCount,
            )

            ReviewCard(
                reviews = reviews,
                onViewAllReviewClick = onReviewClick,
                bestCount = uiState.gameReviewResult.bestCount,
                goodCount = uiState.gameReviewResult.goodCount,
                badCount = uiState.gameReviewResult.badCount,
            )

            if (uiState.isAcceptable) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SmashingButton(
                        buttonStyle = ButtonStyle.DISABLED_ACTIVE,
                        text = "건너뛰기",
                        modifier = Modifier.weight(BTN_WEIGHT),
                        onClick = onNoClick,
                    )
                    SmashingButton(
                        buttonStyle = ButtonStyle.PRIMARY,
                        text = "수락",
                        modifier = Modifier.weight(1f),
                        onClick = onYesClick,
                    )
                }
            }
        }


        if (uiState.isDialogVisible) {
            SmashingDialog(
                title = "경쟁 신청이 완료되었습니다!",
                subtitle = "매칭 관리 탭에서 매칭 정보를 확인해주세요.",
                type = DialogStyle.ALERT,
                confirmText = "바로가기",
                dismissText = "확인",
                onConfirmClick = onConfirmClick,
                onDismissClick = onDialogDismissClick,
                onDismissRequest = onDialogDismissClick,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    SmashingAndroidTheme {
        UserProfileScreen(
            uiState = UserProfileContract.State(),
            reviews = persistentListOf(),
            onReviewClick = {},
            onNoClick = {},
            onYesClick = {},
            onCompeteClick = {},
            onConfirmClick = {},
            onDialogDismissClick = {},
            onBackClick = {},
        )
    }
}
