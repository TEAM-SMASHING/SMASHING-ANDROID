package com.smashing.app.presentation.search.searchmain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.search_apply_btn
import com.smashing.app.R.string.search_filter_empty_subtitle
import com.smashing.app.R.string.search_filter_empty_title
import com.smashing.app.R.string.search_gender
import com.smashing.app.R.string.search_tier
import com.smashing.app.core.designsystem.component.bottomsheet.BottomSheetButtonConfig
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
        viewModel.fetchRegionUsersList(isRefresh = true)
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
                text = uiState.currentTierText ?: stringResource(search_tier),
                onFilterClick = onTierBottomSheetOpen,
                onFilterDelete = onDeleteTierFilter,
            )

            Spacer(modifier = Modifier.width(8.dp))

            MatchingSearchFilterChip(
                style = if (uiState.currentGenderText == null) DEFAULT else VARIANT,
                text = uiState.currentGenderText ?: stringResource(search_gender),
                onFilterClick = onGenderBottomSheetOpen,
                onFilterDelete = onDeleteGenderFilter,
            )
        }

        if (uiState.isTierBottomSheetEnabled) {
            SmashingBottomSheet(
                onDismissRequest = onTierBottomSheetClose,
                title = stringResource(search_tier),
                items = uiState.tierBottomSheetList,
                selectedItem = uiState.selectedTierItem?.tierKName ?: "",
                onItemClick = onTierItemClick,
                optionalButton = BottomSheetButtonConfig(
                    btnText = stringResource(search_apply_btn),
                    contentToBtnPadding = 4.dp,
                    onBtnClick = onTierApplyClick,
                ),
            )
        }

        if (uiState.isGenderBottomSheetEnabled) {
            SmashingBottomSheet(
                onDismissRequest = onGenderBottomSheetClose,
                title = stringResource(search_gender),
                items = uiState.genderBottomSheetList,
                selectedItem = uiState.selectedGenderItem?.genderKName ?: "",
                onItemClick = onGenderItemClick,
                optionalButton = BottomSheetButtonConfig(
                    btnText = stringResource(search_apply_btn),
                    contentToBtnPadding = 4.dp,
                    onBtnClick = onGenderApplyClick,
                ),
            )
        }

        when (uiState.searchRegionUsersUiState) {
            SearchUiState.Idle -> Unit
            SearchUiState.Empty -> {
                SearchEmpty(
                    title = stringResource(search_filter_empty_title),
                    subTitle = stringResource(search_filter_empty_subtitle),
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
                    title = stringResource(search_filter_empty_title),
                    subTitle = stringResource(search_filter_empty_subtitle),
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
