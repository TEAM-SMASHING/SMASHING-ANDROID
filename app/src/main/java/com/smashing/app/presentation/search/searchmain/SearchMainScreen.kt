package com.smashing.app.presentation.search.searchmain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.smashing.app.core.extension.onBottomReached
import com.smashing.app.presentation.search.SearchContract
import com.smashing.app.presentation.search.SearchUiState
import com.smashing.app.presentation.search.SearchViewModel
import com.smashing.app.presentation.search.component.SearchEmpty
import com.smashing.app.presentation.search.searchmain.component.MatchingSearchFilterChip
import com.smashing.app.presentation.search.searchmain.component.SearchTopBar
import com.smashing.app.presentation.search.searchmain.style.FilterStyle.DEFAULT
import com.smashing.app.presentation.search.searchmain.style.FilterStyle.VARIANT


@Composable
fun SearchMainRoute(
    navigateToRegionChange: () -> Unit,
    navigateToSearchInput: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.updateSelectedRegion()
    }

    SearchMainScreen(
        uiState = uiState,
        onLoadMoreSearchList = viewModel::fetchRegionUsersList,
        onRegionSelectClick = navigateToRegionChange,
        onRegionDropdownClick = { },
        onSearchClick = navigateToSearchInput,
        onProfileClick = { userId ->
            navigateToUserProfile(userId)
        },
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
private fun SearchMainScreen(
    uiState: SearchContract.State,
    onLoadMoreSearchList: () -> Unit,
    onRegionSelectClick: () -> Unit,
    onRegionDropdownClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: (String) -> Unit,
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

    //Todo: 스크롤 수정
//    var isFirstLoad by remember { mutableStateOf(true) }
//
//    LaunchedEffect(uiState.searchList) {
//        if (isFirstLoad && uiState.searchList.isNotEmpty()) {
//            listState.scrollToItem(0)
//            isFirstLoad = false
//        }
//    }

    val currentIsLoading = uiState.searchRegionUsersUiState is SearchUiState.Loading

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bgCanvas)
    ) {

        SearchTopBar(
            selectedRegion = uiState.selectedRegion,
            regionItems = uiState.regionItems,
            onRegionDropdownClick = onRegionDropdownClick,
            onSearchClick = onSearchClick,
            onRegionSelectClick = onRegionSelectClick,
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
                items = uiState.tierBottomSheetList,
                selectedItem = uiState.selectedTierItem?.tierKName ?: "",
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
                items = uiState.genderBottomSheetList,
                selectedItem = uiState.selectedGenderItem?.genderKName ?: "",
                contentToBtnPadding = 4.dp,
                btnText = "적용하기",
                onItemClick = onGenderItemClick,
                onBtnClick = onGenderApplyClick,
            )
        }

        when (uiState.searchRegionUsersUiState) {
            SearchUiState.Idle -> Unit
            SearchUiState.Empty -> {
                SearchEmpty(
                    title = "해당 조건에 맞는 유저가 없어요",
                    subTitle = "적용된 필터를 변경해보세요",
                )
            }

            SearchUiState.Loading -> Unit
            SearchUiState.Success -> {
                listState.onBottomReached(
                    threshold = 3,
                    onLoadMore = onLoadMoreSearchList,
                    isLoading = currentIsLoading,
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(horizontal = 16.dp),
                    state = listState,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(
                        items = uiState.searchList,
                    ) {
                        MatchingCard(
                            cardState = MatchingCardState.Search(
                                userId = it.userId,
                                nickname = it.nickname,
                                genderType = it.gender,
                                tierType = it.tierType,
                                onProfileClick = { onProfileClick(it.userId) },
                                winCount = it.wins,
                                loseCount = it.losses,
                                reviewCount = it.reviews,
                            )
                        )
                    }
                }
            }

            else -> {
                SearchEmpty(
                    title = "해당 조건에 맞는 유저가 없어요",
                    subTitle = "적용된 필터를 변경해보세요",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SmashingAndroidTheme {
        SearchMainScreen(
            uiState = SearchContract.State(),
            onLoadMoreSearchList = {},
            onRegionSelectClick = {},
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
