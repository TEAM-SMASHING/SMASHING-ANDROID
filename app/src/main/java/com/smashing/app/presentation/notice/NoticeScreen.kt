package com.smashing.app.presentation.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.R.drawable.ic_arrow_left
import com.smashing.app.core.common.type.NotificationType
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.domain.model.Notification
import com.smashing.app.presentation.notice.component.NoticeItem
import kotlinx.collections.immutable.persistentListOf

@Composable
fun NoticeRoute(
    modifier: Modifier = Modifier,
) {
    NoticeScreen(
        modifier = modifier,
        uiState = NoticeContract.State()
    )
}

@Composable
private fun NoticeScreen(
    uiState: NoticeContract.State,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .statusBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 21.dp,
                    horizontal = 16.dp,
                ),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_left),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterStart),
            )

            Text(
                text = stringResource(R.string.notice),
                color = SmashingTheme.colors.txtPrimary,
                style = SmashingTheme.typography.md.semibold16,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        LazyColumn(
            modifier = Modifier,
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
                    timeAgo = "timeAgo",
                    onItemClick = {},
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
            uiState = NoticeContract.State(
                noticeList = persistentListOf(
                    Notification(
                        notificationId = "1",
                        title = "매칭 결과가 반려되었어요",
                        notificationType = NotificationType.RESULT_REJECTED_SCORE_MISMATCH,
                        description = "\"와쿠와쿠\"님이 결과 입력을 거절했습니다. (사유: 점수 오류)",
                        userId = "323",
                        sportType = SportType.PING_PONG,
                        isRead = false,
                        timeAgo = "2시간 전",
                    ),
                    Notification(
                        notificationId = "2",
                        title = "매칭이 수락 되었어요",
                        description = "\"닝우닝\"(Silver)님이 매칭을 수락했어요! 지금 확인 해볼까요?",
                        notificationType = NotificationType.MATCHING_ACCEPTED,
                        userId = "324",
                        sportType = SportType.TENNIS,
                        isRead = true,
                        timeAgo = "2시간 전",
                    ),
                ),
            ),
        )
    }
}
