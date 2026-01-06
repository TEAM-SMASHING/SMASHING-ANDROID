package com.smashing.app.presentation.region

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.common.state.UiState
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.model.Region
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun RegionRoute(
    modifier: Modifier = Modifier,
    viewModel: RegionViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.searchQuery) {
        snapshotFlow { uiState.searchQuery }
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .debounce(timeoutMillis = 500)
            .collect { query ->
                viewModel.fetchRegion(query)
            }
    }

    RegionScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onRegionSelected = viewModel::updateSelectedRegion,
        modifier = modifier,
    )
}

@Composable
private fun RegionScreen(
    uiState: RegionContract.State,
    onSearchQueryChange: (String) -> Unit,
    onRegionSelected: (Region) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "활동지역검색",
        )

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            placeholder = {
                Text(text = "주소를 입력하세요")
            },
        )
        uiState.selectedRegion?.let { selected ->
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {
                Text(
                    text = "선택된 지역: ${selected.region2depthName}",
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (val loadState = uiState.regionLoadState) {
            is UiState.Idle -> {

            }

            is UiState.Loading -> {
                Text(
                    text = "검색 중...",
                    modifier = Modifier.padding(16.dp),
                )
            }

            is UiState.Failure -> {
                Text(
                    text = "오류: ${loadState.msg}",
                    modifier = Modifier.padding(16.dp),
                )
            }

            is UiState.Success -> {
                RegionList(
                    regions = loadState.data,
                    onRegionClick = onRegionSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            }
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick),
    ) {
        Column {
            Text(
                text = region.addressName,
                modifier = Modifier.padding(16.dp),
            )
            Text(
                text = region.region2depthName,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegionScreenPreview() {
    RegionScreen(
        uiState = RegionContract.State(),
        onSearchQueryChange = {},
        onRegionSelected = {},
    )
}