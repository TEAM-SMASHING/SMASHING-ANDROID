package com.smashing.app.presentation.matching

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R.drawable.img_app_icon
import com.smashing.app.R.string.cancel
import com.smashing.app.R.string.matching_accepted_dialog_description
import com.smashing.app.R.string.matching_accepted_dialog_title
import com.smashing.app.R.string.matching_confirm_empty
import com.smashing.app.R.string.matching_empty_description
import com.smashing.app.R.string.matching_receive_empty
import com.smashing.app.R.string.matching_send_dialog_description
import com.smashing.app.R.string.matching_send_dialog_title
import com.smashing.app.R.string.matching_send_empty
import com.smashing.app.R.string.no
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.toast.LocalToastTrigger
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.onBottomReached
import com.smashing.app.core.extension.openUrl
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.presentation.matching.component.MatchingTabBar
import com.smashing.app.presentation.matching.type.MatchingType

private const val MATCHING_CONTENT_CROSSFADE = "matching_content_crossfade"

@Composable
fun MatchingRoute(
    navigateToSubmit: (
        gameId: String,
        opponentUserId: String,
        opponentNickname: String,
        isFirstAttempt: Boolean,
        submissionId: String?,
    ) -> Unit,
    navigateToConfirm: (
        submissionId: String,
        gameId: String,
        isFirstAttempt: Boolean,
    ) -> Unit,
    navigateToProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    savedInitTab: MatchingType?,
    setSavedInitTab: (MatchingType) -> Unit,
    removeSavedInitTab: () -> Unit,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        savedInitTab?.let { tab ->
            if (tab != uiState.selectedType) {
                viewModel.selectMatchingTab(tab)
            }
            removeSavedInitTab()
        }
    }


    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val showToast = LocalToastTrigger.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MatchingContract.SideEffect.NavigateToSubmit -> navigateToSubmit(
                        sideEffect.gameId,
                        sideEffect.opponentUserId,
                        sideEffect.opponentNickname,
                        sideEffect.isFirstAttempt,
                        sideEffect.submissionId,
                    )

                    is MatchingContract.SideEffect.NavigateToConfirm -> navigateToConfirm(
                        sideEffect.submissionId,
                        sideEffect.gameId,
                        sideEffect.isFirstAttempt,
                    )

                    is MatchingContract.SideEffect.ShowToast -> {
                        showToast.invoke(sideEffect.message)
                    }
                }
            }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshMatchingList()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    MatchingScreen(
        uiState = uiState,
        onLoadMoreMatchingList = viewModel::fetchMatchingList,
        onTabClick = { tab ->
            setSavedInitTab(tab)
            viewModel.selectMatchingTab(tab)
        },
        onDialogDismissClick = viewModel::hideDialogVisible,
        onReceivedAcceptClick = viewModel::acceptReceivedMatching,
        onProfileClick = navigateToProfile,
        onSentCloseClick = viewModel::showDeleteSentMatchingDialog,
        onReceivedSkipClick = viewModel::rejectReceivedMatching,
        onAcceptedMatchingClick = viewModel::handleAcceptedMatchingClick,
        onAcceptedKakaoLinkClick = { kakaoLink -> context.openUrl(kakaoLink) },
        onAcceptedCloseClick = viewModel::showDeleteAcceptedMatchingDialog,
        onConfirmDeleteSentMatching = viewModel::deleteSentMatching,
        onConfirmDeleteAcceptedMatching = viewModel::confirmDeleteAcceptedMatching,
        modifier = modifier,
    )
}

@Composable
private fun MatchingScreen(
    uiState: MatchingContract.State,
    onLoadMoreMatchingList: () -> Unit,
    onTabClick: (MatchingType) -> Unit,
    onDialogDismissClick: () -> Unit,
    onReceivedAcceptClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onProfileClick: (String) -> Unit = {},
    onSentCloseClick: (String) -> Unit = {},
    onReceivedSkipClick: (String) -> Unit = {},
    onAcceptedMatchingClick: (AcceptedMatching) -> Unit = {},
    onAcceptedKakaoLinkClick: (String?) -> Unit = {},
    onAcceptedCloseClick: (String) -> Unit = {},
    onConfirmDeleteSentMatching: () -> Unit = {},
    onConfirmDeleteAcceptedMatching: () -> Unit = {},
) {
    val gridState = rememberLazyGridState()

    LaunchedEffect(uiState.selectedType) {
        gridState.scrollToItem(0)
    }

    val emptyTitle = stringResource(
        when (uiState.selectedType) {
            MatchingType.SEND -> matching_send_empty
            MatchingType.RECEIVE -> matching_receive_empty
            MatchingType.ACCEPTED -> matching_confirm_empty
        }
    )

    val currentUiState = when (uiState.selectedType) {
        MatchingType.SEND -> uiState.sentUiState
        MatchingType.RECEIVE -> uiState.receivedUiState
        MatchingType.ACCEPTED -> uiState.acceptedUiState
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SmashingDefaultTopBar(
            title = "매칭 관리",
            topBarType = TopBarType.DEFAULT,
            onClick = null,
        )

        MatchingTabBar(
            selectedType = uiState.selectedType,
            onTabClick = onTabClick,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        Crossfade(
            targetState = uiState.selectedType to currentUiState,
            label = MATCHING_CONTENT_CROSSFADE,
        ) { (_, state) ->
            when (state) {
                is MatchingUiState.Empty -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(Modifier.weight(171 / 252f))

                        Icon(
                            painter = painterResource(img_app_icon),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .padding(
                                    bottom = 16.dp,
                                ),
                        )

                        Text(
                            text = emptyTitle,
                            style = SmashingTheme.typography.lg.semibold18,
                            color = SmashingTheme.colors.txtSecondary,
                        )

                        Text(
                            text = stringResource(matching_empty_description),
                            style = SmashingTheme.typography.sm.medium14,
                            color = SmashingTheme.colors.txtTertiary,
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                is MatchingUiState.Success -> {
                    MatchingList(
                        uiState = uiState,
                        gridState = gridState,
                        onLoadMoreMatchingList = onLoadMoreMatchingList,
                        onProfileClick = onProfileClick,
                        onSentCloseClick = onSentCloseClick,
                        onReceivedSkipClick = onReceivedSkipClick,
                        onReceivedAcceptClick = onReceivedAcceptClick,
                        onAcceptedMatchingClick = onAcceptedMatchingClick,
                        onAcceptedKakaoLinkClick = onAcceptedKakaoLinkClick,
                        onAcceptedCloseClick = onAcceptedCloseClick,
                    )
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }

        if (uiState.isDialogVisible) {
            when (uiState.selectedType) {
                MatchingType.SEND -> {
                    SmashingDialog(
                        title = stringResource(matching_send_dialog_title),
                        subtitle = stringResource(matching_send_dialog_description),
                        type = DialogStyle.ALERT,
                        confirmText = stringResource(cancel),
                        dismissText = stringResource(no),
                        onConfirmClick = onConfirmDeleteSentMatching,
                        onDismissClick = onDialogDismissClick,
                        onDismissRequest = onDialogDismissClick,
                    )
                }

                MatchingType.ACCEPTED -> {
                    SmashingDialog(
                        title = stringResource(matching_accepted_dialog_title),
                        subtitle = stringResource(matching_accepted_dialog_description),
                        type = DialogStyle.ALERT,
                        confirmText = stringResource(cancel),
                        dismissText = stringResource(no),
                        onConfirmClick = onConfirmDeleteAcceptedMatching,
                        onDismissClick = onDialogDismissClick,
                        onDismissRequest = onDialogDismissClick,
                    )
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun MatchingList(
    uiState: MatchingContract.State,
    gridState: LazyGridState,
    onLoadMoreMatchingList: () -> Unit,
    onProfileClick: (String) -> Unit,
    onSentCloseClick: (String) -> Unit,
    onReceivedSkipClick: (String) -> Unit,
    onReceivedAcceptClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onAcceptedMatchingClick: (AcceptedMatching) -> Unit = {},
    onAcceptedKakaoLinkClick: (String?) -> Unit = {},
    onAcceptedCloseClick: (String) -> Unit = {},
) {
    val currentIsLoading = when (uiState.selectedType) {
        MatchingType.SEND -> uiState.sentUiState is MatchingUiState.Loading
        MatchingType.RECEIVE -> uiState.receivedUiState is MatchingUiState.Loading
        MatchingType.ACCEPTED -> uiState.acceptedUiState is MatchingUiState.Loading
    }

    gridState.onBottomReached(
        threshold = 3,
        onLoadMore = onLoadMoreMatchingList,
        isLoading = currentIsLoading,
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        contentPadding = PaddingValues(bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        modifier = modifier
    ) {
        when (uiState.selectedType) {
            MatchingType.RECEIVE -> items(
                items = uiState.receivedList,
                key = { "${MatchingType.RECEIVE}_${it.matchingId}" }
            ) {
                MatchingCard(
                    cardState = MatchingCardState.Receive(
                        userId = it.userId,
                        nickname = it.nickname,
                        genderType = it.genderType,
                        tierType = it.tierType,
                        winCount = it.winCount,
                        loseCount = it.loseCount,
                        reviewCount = it.reviewCount,
                        onProfileClick = { onProfileClick(it.userId) },
                        onSkipClick = { onReceivedSkipClick(it.matchingId) },
                        onAcceptClick = { onReceivedAcceptClick(it.matchingId) },
                    ),
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(durationMillis = 300),
                        fadeOutSpec = tween(durationMillis = 300),
                        placementSpec = tween(durationMillis = 300),
                    ),
                )
            }

            MatchingType.SEND -> items(
                items = uiState.sentList,
                key = { "${MatchingType.SEND}_${it.matchingId}" }
            ) {
                MatchingCard(
                    cardState = MatchingCardState.Send(
                        userId = it.userId,
                        nickname = it.nickname,
                        genderType = it.genderType,
                        tierType = it.tierType,
                        onProfileClick = { onProfileClick(it.userId) },
                        onCloseClick = { onSentCloseClick(it.matchingId) },
                        winCount = it.winCount,
                        loseCount = it.loseCount,
                        reviewCount = it.reviewCount,
                    ),
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(durationMillis = 300),
                        fadeOutSpec = tween(durationMillis = 300),
                        placementSpec = tween(durationMillis = 300),
                    ),
                )
            }

            MatchingType.ACCEPTED -> items(
                items = uiState.acceptedList,
                key = { "${MatchingType.ACCEPTED}_${it.gameId}" }
            ) { matching ->
                val isCanceled = matching.resultStatus == GameResultStatusType.CANCELED

                Box(
                    modifier = Modifier
                        .animateItem(
                            fadeInSpec = tween(300),
                            fadeOutSpec = tween(300),
                            placementSpec = tween(300),
                        )
                ) {
                    MatchingCard(
                        cardState = MatchingCardState.Confirm(
                            userId = matching.userId,
                            nickname = matching.nickname,
                            genderType = matching.genderType,
                            tierType = matching.tierType,
                            onProfileClick = { onProfileClick(matching.userId) },
                            onConfirmClick = { onAcceptedMatchingClick(matching) },
                            onKakaoLinkClick = { onAcceptedKakaoLinkClick(matching.openChatUrl) },
                            onCloseClick = { onAcceptedCloseClick(matching.gameId) },
                            gameStatusType = matching.resultStatus,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isCanceled) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(SmashingTheme.colors.bgDimmed)
                                .pointerInput(Unit) {}
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingScreenPreview() {
    SmashingAndroidTheme {
        MatchingScreen(
            uiState = MatchingContract.State(
                receivedUiState = MatchingUiState.Empty,
            ),
            onLoadMoreMatchingList = {},
            onTabClick = {},
            onDialogDismissClick = {},
            onReceivedAcceptClick = {},
            onProfileClick = {},
            onSentCloseClick = {},
            onReceivedSkipClick = {},
            onAcceptedMatchingClick = {},
            onAcceptedKakaoLinkClick = {},
            onAcceptedCloseClick = {},
            onConfirmDeleteSentMatching = {},
            onConfirmDeleteAcceptedMatching = {},
            modifier = Modifier
                .background(Color.Black),
        )
    }
}
