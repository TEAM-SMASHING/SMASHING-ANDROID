package com.smashing.app.presentation.matching

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.common.type.TierType
import com.smashing.app.data.model.matching.ReceivedMatchingItem
import com.smashing.app.data.model.matching.RequesterSummary
import com.smashing.app.presentation.matching.component.ReceivedMatchingCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.OffsetDateTime

@Composable
fun MatchingRoute(
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MatchingScreen(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun MatchingScreen(
    uiState: MatchingContract.State,
    modifier: Modifier = Modifier,
) {
    ReceivedMatchingList(
        items = uiState.receivedMatchingList,
        modifier = modifier,
    )
}

// TODO 추후 삭제 예정
@Composable
private fun ReceivedMatchingList(
    items: ImmutableList<ReceivedMatchingItem>,
    modifier: Modifier = Modifier,
    onItemClick: (ReceivedMatchingItem) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = items,
            key = { it.matchingId },
        ) { item ->
            ReceivedMatchingCard(
                item = item,
                onClick = { onItemClick(item) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingScreenPreview() {
    val mockItems = persistentListOf(
        ReceivedMatchingItem(
            matchingId = "matching_001",
            createdAt = OffsetDateTime.now(),
            status = "REQUESTED",
            requesterSummary = RequesterSummary(
                userId = "user_001",
                nickname = "윤서",
                gender = "FEMALE",
                tierType = TierType.GOLD_1,
                winCount = 18,
                loseCount = 7,
                reviewCount = 12,
            ),
        ),
        ReceivedMatchingItem(
            matchingId = "matching_002",
            createdAt = OffsetDateTime.now().minusHours(1),
            status = "REQUESTED",
            requesterSummary = RequesterSummary(
                userId = "user_002",
                nickname = "민준",
                gender = "MALE",
                tierType = TierType.SILVER_2,
                winCount = 10,
                loseCount = 5,
                reviewCount = 8,
            ),
        ),
    )
    
    ReceivedMatchingList(items = mockItems)
}
