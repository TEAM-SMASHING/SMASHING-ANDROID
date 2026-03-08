package com.smashing.app.presentation.profile.userprofile

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R.string.profile
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.toast.LocalToastTrigger
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.component.bottomsheet.SmashingBottomSheet
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
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.SideEffect.ShowToast
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

private const val BTN_WEIGHT = 131f / 185f

@Composable
fun UserProfileRoute(
    navigateToReview: (String?) -> Unit,
    navigateToSentMatching: () -> Unit,
    navigateToReport: () -> Unit,
    onBlockClick: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val show = LocalToastTrigger.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToAllReview -> navigateToReview(sideEffect.userId)
                    is ShowToast -> show.invoke(sideEffect.content)
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
        navigateToReport = navigateToReport,
        onBlockClick = onBlockClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
    navigateToReport: () -> Unit,
    onBlockClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {

    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    var showMenuBottomSheet by remember { mutableStateOf(false) }
    var showBlockDialog by remember { mutableStateOf(false) }
    val density = LocalDensity.current


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bgCanvas)
            .systemBarsPadding(),
    ) {

        SmashingDefaultTopBar(
            title = stringResource(profile),
            topBarType = TopBarType.BACK_WITH_MENU,
            onClick = onBackClick,
            onMenuClick = { showMenuBottomSheet = true },
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            ) {
                UserProfileCard(
                    nickname = uiState.userProfileInfo.nickname,
                    gender = uiState.userProfileInfo.genderType,
                    tierType = uiState.activeProfile.tierType,
                    winCount = uiState.activeProfile.winCount,
                    loseCount = uiState.activeProfile.loseCount,
                    reviewCount = uiState.userProfileInfo.reviewCount,
                    onCompeteClick = onCompeteClick,
                    isCompeteEnabled = uiState.isChallengeable,
                )

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


                ProfileTierBox(
                    tierType = uiState.activeProfile.tierType,
                    sportProfileList = uiState.sportProfileList.toImmutableList(),
                    selectedProfileId = uiState.selectedSportProfileId,
                    tierIconResId = uiState.activeProfile.tierType.img(),
                    progress = ((uiState.activeProfile.lp - uiState.activeProfile.minLp).toFloat() / (uiState.activeProfile.maxLp - uiState.activeProfile.minLp).toFloat()),
                    lpStatus = (uiState.activeProfile.maxLp - uiState.activeProfile.lp) + 1,
                    totalLp = (uiState.activeProfile.maxLp) + 1,
                )

                ProfileStatsBar(
                    winCount = uiState.activeProfile.winCount,
                    loseCount = uiState.activeProfile.loseCount,
                )

                ReviewCard(
                    reviews = reviews,
                    onViewAllReviewClick = onReviewClick,
                    bestCount = uiState.gameReviewResult.bestCount,
                    goodCount = uiState.gameReviewResult.goodCount,
                    badCount = uiState.gameReviewResult.badCount,
                )

                if (uiState.isAcceptable && bottomBarHeight > 0.dp) {
                    Spacer(modifier = Modifier.height(bottomBarHeight + 49.dp))
                } else {
                    Spacer(modifier = Modifier.height(37.dp))
                }
            }
            if (uiState.isAcceptable) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.bgCanvas)
                        .padding(horizontal = 16.dp)
                        .padding(top = 12.dp, bottom = 52.dp)
                        .align(Alignment.BottomCenter)
                        .onGloballyPositioned { coordinates ->
                            bottomBarHeight = with(density) {
                                coordinates.size.height.toDp()
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
        }

        if (showMenuBottomSheet) {
            SmashingBottomSheet(
                items = persistentListOf("신고하기", "차단하기"),
                selectedItem = "",
                onItemClick = { item ->
                    showMenuBottomSheet = false
                    when (item) {
                        "신고하기" -> navigateToReport()
                        "차단하기" -> showBlockDialog = true
                    }
                },
                onDismissRequest = { showMenuBottomSheet = false },
                itemTextColor = {if(it == "차단하기") colors.txtRed else null},
            )
        }

        if (showBlockDialog) {
            SmashingDialog(
                title = "정말 차단하시겠습니까?",
                subtitle = "차단 시 서로 프로필과 매칭에서\n보이지 않게 됩니다.",
                type = DialogStyle.ALERT,
                confirmText = "차단하기",
                dismissText = "아니오",
                onConfirmClick = {
                    showBlockDialog = false
                    onBlockClick()
                },
                onDismissClick = { showBlockDialog = false },
                onDismissRequest = { showBlockDialog = false },
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
            navigateToReport = {},
            onBlockClick = {},
        )
    }
}
