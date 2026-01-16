package com.smashing.app.presentation.region

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndSelectAll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.core.common.state.UiState
import com.smashing.app.core.designsystem.component.topbar.SmashingSearchTopBar
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.domain.model.Region
import com.smashing.app.presentation.region.RegionContract.SideEffect.NavigateUpWithResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun RegionRoute(
    navigateToRegionChange: (Region) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateUpWithResult -> {
                        navigateToRegionChange(
                            Region(
                                sideEffect.addressName,
                                sideEffect.cityName,
                                sideEffect.districtName,
                            )
                        )
                    }
                }
            }
    }

    RegionScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onRegionSelected = viewModel::updateSelectedRegion,
        navigateUp = navigateUp,
        modifier = modifier,
    )
}

@Composable
private fun RegionScreen(
    uiState: RegionContract.State,
    onSearchQueryChange: (String) -> Unit,
    onRegionSelected: (Region) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchState = rememberTextFieldState(initialText = uiState.searchQuery)

    LaunchedEffect(uiState.searchQuery) {
        val currentText = searchState.text.toString()
        if (currentText != uiState.searchQuery) {
            searchState.setTextAndSelectAll(uiState.searchQuery)
        }
    }

    LaunchedEffect(searchState.text.toString()) {
        val currentText = searchState.text.toString()
        if (currentText != uiState.searchQuery) {
            onSearchQueryChange(currentText)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SmashingTheme.colors.bgCanvas,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        SmashingSearchTopBar(
            searchState = searchState,
            placeholder = "주소를 검색해주세요",
            onBackClick = navigateUp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.regionLoadState is UiState.Success) {
            RegionList(
                regions = uiState.regionLoadState.data,
                onRegionClick = onRegionSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        vertical = 5.dp,
                    ),
            )
        }
    }
}

@Composable
private fun RegionList(
    regions: ImmutableList<Region>,
    onRegionClick: (Region) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(
            items = regions,
        ) { region ->
            RegionItem(
                region = region,
                onClick = { onRegionClick(region) },
            )
        }
    }
}

@Composable
private fun RegionItem(
    region: Region,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClicked: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isClicked) SmashingTheme.colors.bgSurface else Color.Transparent,
            )
            .noRippleClickable(onClick = onClick),
    ) {
        Text(
            text = region.addressName,
            color = SmashingTheme.colors.txtSecondary,
            style = SmashingTheme.typography.sm.medium14,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 17.dp,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegionScreenPreview() {
    RegionScreen(
        uiState = RegionContract.State(
            searchQuery = "서울",
            selectedRegion = Region(
                addressName = "서울특별시 종로구",
                cityName = "서울특별시",
                districtName = "종로구",
            ),
            regionLoadState = UiState.Success(
                listOf(
                    Region(
                        addressName = "서울특별시 종로구",
                        cityName = "서울특별시",
                        districtName = "종로구",
                    ),
                    Region(
                        addressName = "서울특별시 강남구",
                        cityName = "서울특별시",
                        districtName = "강남구",
                    )
                ).toImmutableList(),
            )
        ),
        onSearchQueryChange = {},
        onRegionSelected = {},
        navigateUp = {},
    )
}
