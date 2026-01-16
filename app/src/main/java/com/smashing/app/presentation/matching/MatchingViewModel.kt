package com.smashing.app.presentation.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.matching.type.MatchingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val matchingRepository: MatchingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(getDummyState())
    val uiState = _uiState.asStateFlow()

    fun selectMatchingType(type: MatchingType) {
        updateMatchingType(type)
        when (type) {
            MatchingType.RECEIVE -> fetchReceivedMatchingList(true)
            MatchingType.SEND -> {
                //  fetchSentMatchingList()
            }

            MatchingType.ACCEPTED -> {
                // fetchAcceptedMatchingList()
            }
        }
    }

    private fun updateMatchingType(type: MatchingType) = _uiState.update {
        it.copy(selectedType = type)
    }

    fun showDialogVisible() = _uiState.update {
        it.copy(isDialogVisible = true)
    }

    fun hideDialogVisible() = _uiState.update {
        it.copy(isDialogVisible = false)
    }

    fun fetchMatchingList() {
        when (uiState.value.selectedType) {
            MatchingType.RECEIVE -> fetchReceivedMatchingList()
            MatchingType.SEND -> {}
            MatchingType.ACCEPTED -> {}
        }
    }

    fun fetchReceivedMatchingList(isRefresh: Boolean = false) = viewModelScope.launch {
        matchingRepository.getMeReceivedMatchingList(
            snapshotAt = if (isRefresh) null else _uiState.value.receivedCursor.snapshotAt,
            cursor = if (isRefresh) null else _uiState.value.receivedCursor.nextCursor,
            size = CURSOR_SIZE,
        ).onSuccess { cursorPage ->
            Timber.tag("MatchingViewModel").d("fetchReceivedMatchingList: $cursorPage")
            _uiState.update { state ->
                state.copy(
                    receivedList = if (isRefresh) {
                        cursorPage.items.toImmutableList()
                    } else {
                        (state.receivedList + cursorPage.items).toImmutableList()
                    },
                    receivedCursor = cursorPage.cursor,
                    loadState = if (cursorPage.items.isEmpty() && isRefresh) {
                        MatchingUiState.Empty
                    } else {
                        MatchingUiState.Success
                    },
                )
            }
        }.onFailure { throwable ->
            Timber.tag("MatchingViewModel").d("fetchReceivedMatchingList: ${throwable.message}")
        }
    }

    // TODO 더미 데이터 삭제 예정
    private fun getDummyState(): MatchingContract.State {
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
                createdAt = "",
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
                createdAt = "",
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

        return MatchingContract.State(
            loadState = MatchingUiState.Success,
            selectedType = MatchingType.ACCEPTED,
            receivedCursor = Cursor(),
            sentCursor = Cursor(),
            acceptedCursor = Cursor(),
            receivedList = dummyReceivedList,
            sentList = dummySentList,
            acceptedList = dummyAcceptedList,
        )
    }

    companion object {
        private const val CURSOR_SIZE = 20L
    }
}
