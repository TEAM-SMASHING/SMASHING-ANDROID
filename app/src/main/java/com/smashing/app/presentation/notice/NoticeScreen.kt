package com.smashing.app.presentation.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.core.designsystem.component.appicon.AppIcon
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.onBottomReached
import com.smashing.app.data.type.NotificationType
import com.smashing.app.data.type.SportType
import com.smashing.app.domain.model.Notification
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.notice.component.NoticeItem
import kotlinx.collections.immutable.toPersistentList

@Composable
fun NoticeRoute(
    navigateUp: () -> Unit,
    navigateToMatching: (MatchingType) -> Unit,
    navigateToConfirmReview: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoticeScreen(
        modifier = modifier,
        uiState = uiState,
        onBackBtnClick = navigateUp,
        onLoadMore = viewModel::loadMore,
        onNoticeClick = { notice ->
            viewModel.readNotification(notice.notificationId)
            when (notice.notificationType) {
                NotificationType.MATCHING_REQUESTED -> {
                    navigateToMatching(MatchingType.RECEIVE)
                }

                NotificationType.MATCHING_ACCEPTED,
                NotificationType.MATCHING_RESULT_SUBMITTED,
                NotificationType.RESULT_REJECTED_SCORE_MISMATCH,
                NotificationType.RESULT_REJECTED_WIN_LOSE_REVERSED,
                NotificationType.RESULT_REJECTED_SCORE_AND_WIN_LOSE_MISMATCH,
                NotificationType.RESULT_REJECTED_GAME_NOT_PLAYED_YET,
                    -> navigateToMatching(MatchingType.ACCEPTED)

                NotificationType.REVIEW_RECEIVED -> {
                    notice.relatedId?.let { reviewId ->
                        navigateToConfirmReview(reviewId)
                    }
                }
            }
        },
    )
}

@Composable
private fun NoticeScreen(
    uiState: NoticeContract.State,
    onBackBtnClick: () -> Unit,
    onLoadMore: () -> Unit,
    onNoticeClick: (Notification) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
    ) {
        SmashingDefaultTopBar(
            title = stringResource(R.string.notice),
            topBarType = TopBarType.BACK,
            onClick = onBackBtnClick,
        )

        if (uiState.loadState is NoticeUiState.Empty) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Spacer(Modifier.weight(230f / 330f))

                AppIcon(
                    title = "아직 받은 후기가 없어요",
                    isFilled = false,
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
                        userId = it.userId,
                        sportType = it.sportType,
                        isRead = it.isRead,
                        timeAgo = it.timeAgo,
                        nickname = it.nickname,
                        onItemClick = { onNoticeClick(it) },
                    )
                }
            }

            lazyListState.onBottomReached(
                threshold = 3,
                onLoadMore = onLoadMore,
                isLoading = uiState.loadState is NoticeUiState.Loading,
            )

//            if (uiState.isChangeDialogVisible) {
//                SmashingDialog(
//                    title = "${uiState.selectedNoticeItem.sportType.sportName}로 종목을 변경하시겠어요?",
//                    subtitle = "종목은 재변경 가능합니다.",
//                    confirmText = "변경하기",
//                    dismissText = "아니요",
//                    onConfirmClick =
//                )
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeScreenPreview() {
    val mockList = List(20) { index ->
        Notification(
            notificationId = index.toString(),
            title = "알림 제목 $index",
            description = "이것은 $index 번째 알림 설명입니다.",
            notificationType = if (index % 2 == 0) NotificationType.MATCHING_ACCEPTED else NotificationType.RESULT_REJECTED_SCORE_MISMATCH,
            userId = "user_$index",
            sportType = if (index % 2 == 0) SportType.TENNIS else SportType.PING_PONG,
            isRead = index > 5,
            nickname = "a",
            timeAgo = "${index}분 전",
            linkUrl = "/api/v1/reviews/review_$index",
            relatedId = "review_$index",
        )
    }.toPersistentList()

    SmashingAndroidTheme {
        NoticeScreen(
            uiState = NoticeContract.State(noticeList = mockList),
            onBackBtnClick = {},
            onLoadMore = {},
            onNoticeClick = {},
        )
    }
}
