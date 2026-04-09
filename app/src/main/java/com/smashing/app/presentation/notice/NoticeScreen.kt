package com.smashing.app.presentation.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.string.no
import com.smashing.app.R.string.notice_change_dialog_change_btn
import com.smashing.app.R.string.notice_change_dialog_subtitle
import com.smashing.app.R.string.notice_change_dialog_title
import com.smashing.app.core.designsystem.component.appicon.AppIcon
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.state.TopBarState
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.onBottomReached
import com.smashing.app.data.model.notification.Notification
import com.smashing.app.data.type.SportType
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.notice.component.NoticeItem


@Composable
fun NoticeRoute(
    navigateUp: () -> Unit,
    navigateToMatching: (MatchingType) -> Unit,
    navigateToConfirmReview: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NoticeContract.SideEffect.NavigateToMatching -> {
                        navigateToMatching(sideEffect.type)
                    }

                    is NoticeContract.SideEffect.NavigateToConfirmReview -> {
                        navigateToConfirmReview(sideEffect.reviewId)
                    }
                }
            }
    }

    NoticeScreen(
        modifier = modifier,
        uiState = uiState,
        onBackBtnClick = navigateUp,
        onLoadMore = viewModel::loadMore,
        onNoticeClick = viewModel::onNoticeClick,
        onConfirmChangeProfile = viewModel::changeMyProfile,
        onDismissChangeProfile = { viewModel.updateIsChangeDialogVisible(false) },
    )
}

@Composable
private fun NoticeScreen(
    uiState: NoticeContract.State,
    onBackBtnClick: () -> Unit,
    onLoadMore: () -> Unit,
    onNoticeClick: (Notification) -> Unit,
    onConfirmChangeProfile: () -> Unit,
    onDismissChangeProfile: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .systemBarsPadding(),
    ) {
        SmashingDefaultTopBar(
            state = TopBarState.Back(
                title = stringResource(R.string.notice),
                onBackClick = onBackBtnClick,
            ),
        )

        if (uiState.loadState is NoticeUiState.Empty) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Spacer(Modifier.weight(230f / 330f))

                AppIcon(
                    title = "아직 받은 알림이 없어요",
                    isFilled = false,
                    modifier = modifier
                        .align(alignment = Alignment.CenterHorizontally),
                )

                Spacer(Modifier.weight(1f))
            }
        } else {
            LazyColumn(
                state = lazyListState,
            ) {
                items(
                    items = uiState.noticeList,
                    key = { it.notificationId },
                ) {
                    NoticeItem(
                        title = it.title,
                        description = it.description,
                        isRead = it.isRead,
                        timeAgo = it.timeAgo,
                        onItemClick = { onNoticeClick(it) },
                    )
                }
            }

            lazyListState.onBottomReached(
                threshold = 3,
                onLoadMore = onLoadMore,
                isLoading = uiState.loadState is NoticeUiState.Loading,
            )

            if (uiState.isChangeDialogVisible) {
                val sport = uiState.targetChangeSport.sportType
                val sportText = sport.sportName + if (sport == SportType.BADMINTON) "으로" else "로"

                SmashingDialog(
                    title = stringResource(notice_change_dialog_title, sportText),
                    onDismissClick = onDismissChangeProfile,
                    subtitle = stringResource(notice_change_dialog_subtitle),
                    type = DialogStyle.ALERT,
                    confirmText = stringResource(notice_change_dialog_change_btn),
                    dismissText = stringResource(no),
                    onConfirmClick = onConfirmChangeProfile,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeScreenPreview() {
    SmashingAndroidTheme {
        NoticeScreen(
            uiState = NoticeContract.State(loadState = NoticeUiState.Empty),
            onBackBtnClick = {},
            onLoadMore = {},
            onNoticeClick = {},
            onConfirmChangeProfile = {},
            onDismissChangeProfile = {},
        )
    }
}
