package com.smashing.app.presentation.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.drawable.ic_arrow_left
import com.smashing.app.data.type.NotificationType
import com.smashing.app.data.type.SportType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.domain.model.Notification
import com.smashing.app.presentation.notice.component.NoticeItem
import kotlinx.collections.immutable.toPersistentList

@Composable
fun NoticeRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoticeScreen(
        modifier = modifier,
        onBackBtnClick = navigateUp,
        uiState = uiState,
    )
}

@Composable
private fun NoticeScreen(
    uiState: NoticeContract.State,
    onBackBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
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
                    .align(Alignment.CenterStart)
                    .noRippleClickable(onClick = onBackBtnClick),
            )

            Text(
                text = stringResource(R.string.notice),
                color = SmashingTheme.colors.txtPrimary,
                style = SmashingTheme.typography.md.semibold16,
                modifier = Modifier.align(Alignment.Center),
            )
        }

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
                    onItemClick = {}, // TODO 추후 라우팅 로직 추가
                )
            }
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
        )
    }.toPersistentList()

    SmashingAndroidTheme {
        NoticeScreen(
            uiState = NoticeContract.State(noticeList = mockList),
            onBackBtnClick = {},
        )
    }
}
