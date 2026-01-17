package com.smashing.app.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.designsystem.component.bottomsheet.SmashingBottomSheet
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.search.component.MatchingSearchFilterChip
import com.smashing.app.presentation.search.component.SearchTopBar
import com.smashing.app.presentation.search.style.FilterStyle.DEFAULT
import com.smashing.app.presentation.search.style.FilterStyle.VARIANT
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SearchRoute(
    navigateToSearchInput: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onRegionDropdownClick = viewModel::updateSelectedRegion,
        onSearchClick = navigateToSearchInput,
        onProfileClick = {},
        onTierItemClick = viewModel::updateSelectedTierItem,
        onGenderItemClick = viewModel::updateSelectedGenderItem,
        onTierBottomSheetOpen = viewModel::openTierBottomSheet,
        onGenderBottomSheetOpen = viewModel::openGenderBottomSheet,
        onTierBottomSheetClose = viewModel::closeTierBottomSheet,
        onGenderBottomSheetClose = viewModel::closeGenderBottomSheet,
        onTierApplyClick = viewModel::applyTierItem,
        onGenderApplyClick = viewModel::applyGenderItem,
        onDeleteTierFilter = viewModel::clearFilterTier,
        onDeleteGenderFilter = viewModel::clearFilterGender,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    uiState: SearchContract.State,
    onRegionDropdownClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTierItemClick: (String) -> Unit,
    onGenderItemClick: (String) -> Unit,
    onTierBottomSheetOpen: () -> Unit,
    onGenderBottomSheetOpen: () -> Unit,
    onTierBottomSheetClose: () -> Unit,
    onGenderBottomSheetClose: () -> Unit,
    onTierApplyClick: () -> Unit,
    onGenderApplyClick: () -> Unit,
    onDeleteTierFilter: () -> Unit,
    onDeleteGenderFilter: () -> Unit,
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
            selectedRegion = uiState.selectedRegion,
            onRegionDropdownClick = onRegionDropdownClick,
            onSearchClick = onSearchClick,
            onRegionSelectClick = {},
            onReginItemClick = {},
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            MatchingSearchFilterChip(
                style = if (uiState.currentTierText == null) DEFAULT else VARIANT,
                text = uiState.currentTierText ?: "티어",
                onFilterClick = onTierBottomSheetOpen,
                onFilterDelete = onDeleteTierFilter,
            )

            Spacer(modifier = Modifier.width(8.dp))

            MatchingSearchFilterChip(
                style = if (uiState.currentGenderText == null) DEFAULT else VARIANT,
                text = uiState.currentGenderText ?: "성별",
                onFilterClick = onGenderBottomSheetOpen,
                onFilterDelete = onDeleteGenderFilter,
            )
        }

        if (uiState.isTierBottomSheetEnabled) {
            SmashingBottomSheet(
                onDismissRequest = onTierBottomSheetClose,
                title = "티어",
                items = persistentListOf(
                    "아이언",
                    "브론즈",
                    "실버",
                    "골드",
                    "플래티넘",
                    "다이아",
                    "챌린저",
                ),
                selectedItem = "${uiState.selectedTierItem}",
                contentToBtnPadding = 4.dp,
                btnText = "적용하기",
                onItemClick = onTierItemClick,
                onBtnClick = onTierApplyClick,
            )
        }

        if (uiState.isGenderBottomSheetEnabled) {
            SmashingBottomSheet(
                onDismissRequest = onGenderBottomSheetClose,
                title = "성별",
                items = persistentListOf(
                    "남성",
                    "여성",
                    "남여 모두",
                ),
                selectedItem = "${uiState.selectedGenderItem}",
                contentToBtnPadding = 4.dp,
                btnText = "적용하기",
                onItemClick = onGenderItemClick,
                onBtnClick = onGenderApplyClick,
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
            onRegionDropdownClick = {},
            onSearchClick = {},
            onProfileClick = {},
            onTierItemClick = {},
            onGenderItemClick = {},
            onTierBottomSheetOpen = {},
            onGenderBottomSheetOpen = {},
            onTierBottomSheetClose = {},
            onGenderBottomSheetClose = {},
            onTierApplyClick = {},
            onGenderApplyClick = {},
            onDeleteTierFilter = {},
            onDeleteGenderFilter = {},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
