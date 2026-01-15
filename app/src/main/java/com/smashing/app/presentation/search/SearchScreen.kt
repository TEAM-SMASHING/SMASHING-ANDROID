package com.smashing.app.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.search.component.MatchingSearchFilterChip
import com.smashing.app.presentation.search.component.SearchTopBar
import com.smashing.app.presentation.search.style.FilterStyle.DEFAULT
import com.smashing.app.presentation.search.style.FilterStyle.VARIANT


@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onProfileClick = {},
        modifier = modifier,
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchContract.State,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val listState = rememberLazyGridState()

    LaunchedEffect(uiState.searchList) {
        listState.scrollToItem(0)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {

        SearchTopBar(
            onRegionSelectClick = {},
            onReginItemClick = {},
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            MatchingSearchFilterChip(
                style = DEFAULT,
                text = "티어",
                onFilterClick = {},
                onFilterDelete = {},
            )

            Spacer(modifier = Modifier.width(8.dp))

            MatchingSearchFilterChip(
                style = VARIANT,
                text = "브론즈",
                onFilterClick = {},
                onFilterDelete = {},
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(
                items = uiState.searchList,
                key = { it.userId },
            ) {
                MatchingCard(
                    cardState = MatchingCardState.Search(
                        userId = it.userId,
                        nickname = it.nickname,
                        genderType = it.gender,
                        tierType = it.tierId,
                        onProfileClick = onProfileClick,
                        winCount = it.wins,
                        loseCount = it.losses,
                        reviewCount = it.reviews.toLong(),
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SmashingAndroidTheme {
        SearchScreen(
            uiState = SearchContract.State(),
            onProfileClick = {},
            modifier = Modifier.background(color = colors.bgCanvas)
        )
    }
}
