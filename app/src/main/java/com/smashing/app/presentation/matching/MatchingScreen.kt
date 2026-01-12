package com.smashing.app.presentation.matching

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smashing.app.R
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.presentation.matching.component.MatchingTabBar
import com.smashing.app.presentation.matching.type.MatchingType
import kotlinx.collections.immutable.persistentListOf
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Composable
fun MatchingRoute(
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val gridState = rememberLazyGridState()

    MatchingScreen(
        uiState = MatchingContract.State(),
        gridState = gridState,
        modifier = modifier,
    )
}

@Composable
private fun MatchingScreen(
    uiState: MatchingContract.State,
    gridState: LazyGridState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.matching_manage),
            style = SmashingTheme.typography.md.semibold16,
            color = SmashingTheme.colors.txtPrimary,
            modifier = Modifier
                .padding(
                    vertical = 21.dp,
                ),
        )

        MatchingTabBar(
            selectedType = uiState.selectedType,
            onTabClick = { /*TODO 탭 클릭 추가*/ },
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            contentPadding = PaddingValues(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
            verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        ) {
            when (uiState.selectedType) {
                MatchingType.SEND -> items(uiState.sendList) {
                    MatchingCard(
                        cardState = MatchingCardState.Send(
                            userId = it.userId,
                            nickname = it.nickname,
                            genderType = it.genderType,
                            tierType = it.tierType,
                            onProfileClick = {},
                            onCloseClick = {},
                            winCount = it.winCount,
                            loseCount = it.loseCount,
                            reviewCount = it.reviewCount,
                        ),
                    )
                }

                MatchingType.RECEIVE -> items(uiState.receiveList) {
                    MatchingCard(
                        cardState = MatchingCardState.Receive(
                            userId = it.userId,
                            nickname = it.nickname,
                            genderType = it.genderType,
                            tierType = it.tierType,
                            winCount = it.winCount,
                            loseCount = it.loseCount,
                            reviewCount = it.reviewCount,
                            onProfileClick = {},
                            onSkipClick = {},
                            onAcceptClick = {},
                        ),
                    )
                }

                MatchingType.ACCEPTED -> items(uiState.acceptedList) {
                    MatchingCard(
                        cardState = MatchingCardState.Confirm(
                            userId = it.userId,
                            nickname = it.nickname,
                            genderType = it.genderType,
                            tierType = it.tierType,
                            onProfileClick = {},
                            onConfirmClick = {},
                            onKakaoLinkClick = {},
                            onCloseClick = {},
                        ),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingScreenPreview() {
    SmashingAndroidTheme {
        // TODO 더미 데이터 삭제 예정
        val dummyAcceptedList = persistentListOf(
            AcceptedMatching(
                matchingId = "matching_accepted_1",
                gameId = "game_1",
                userId = "user_101",
                nickname = "스매셔김",
                genderType = GenderType.MALE,
                tierType = TierType.BRONZE_2,
                openChatUrl = "https://open.kakao.com/o/example1",
                isResultBannerBlocked = false,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
            AcceptedMatching(
                matchingId = "matching_accepted_2",
                gameId = "game_2",
                userId = "user_102",
                nickname = "배드민턴왕",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                openChatUrl = "https://open.kakao.com/o/example2",
                isResultBannerBlocked = true,
                cooldownUntil = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6),
            ),
        )

        val dummyReceivedList = persistentListOf(
            ReceivedMatching(
                matchingId = "matching_received_1",
                userId = "user_201",
                nickname = "셔틀콕러버",
                genderType = GenderType.MALE,
                tierType = TierType.BRONZE_1,
                reviewCount = 12,
                winCount = 8,
                loseCount = 3,
            ),
            ReceivedMatching(
                matchingId = "matching_received_2",
                userId = "user_202",
                nickname = "코트지배자",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_2,
                reviewCount = 27,
                winCount = 21,
                loseCount = 10,
            ),
        )

        val dummySentList = persistentListOf(
            SentMatching(
                matchingId = "matching_sent_1",
                userId = "user_301",
                nickname = "드롭샷마스터",
                genderType = GenderType.MALE,
                tierType = TierType.BRONZE_3,
                reviewCount = 5,
                winCount = 3,
                loseCount = 1,
            ),
            SentMatching(
                matchingId = "matching_sent_2",
                userId = "user_302",
                nickname = "백핸드요정",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                reviewCount = 18,
                winCount = 14,
                loseCount = 6,
            ),
            SentMatching(
                matchingId = "matching_sent_2",
                userId = "user_302",
                nickname = "백핸드요정",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                reviewCount = 18,
                winCount = 14,
                loseCount = 6,
            ),
        )

        val dummyMatchingState = MatchingContract.State(
            selectedType = MatchingType.RECEIVE,
            receiveList = dummyReceivedList,
            sendList = dummySentList,
            acceptedList = dummyAcceptedList,
        )

        MatchingScreen(
            gridState = rememberLazyGridState(),
            uiState = dummyMatchingState,
            modifier = Modifier
                .background(Color.Black),
        )
    }
}
