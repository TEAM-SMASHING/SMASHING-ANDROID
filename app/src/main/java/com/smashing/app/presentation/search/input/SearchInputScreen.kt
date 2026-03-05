package com.smashing.app.presentation.search.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.search_placeholder
import com.smashing.app.core.designsystem.component.topbar.SmashingSearchTopBar
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.presentation.search.SearchContract
import com.smashing.app.presentation.search.SearchUiState
import com.smashing.app.presentation.search.SearchViewModel
import com.smashing.app.presentation.search.component.SearchEmpty
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SearchInputRoute(
    navigateToSearchMain: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.clearSearchInput()
    }

    SearchInputScreen(
        uiState = uiState,
        items = uiState.suggestions,
        searchState = viewModel.searchInputState,
        onBackClick = navigateToSearchMain,
        onSuggestionItemClick = { userId ->
            navigateToUserProfile(userId)
        },
        modifier = modifier,
    )
}

@Composable
private fun SearchInputScreen(
    uiState: SearchContract.State,
    items: ImmutableList<SuggestionItemModel>,
    searchState: TextFieldState,
    onBackClick: () -> Unit,
    onSuggestionItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bgCanvas)
            .systemBarsPadding(),
    ) {
        SmashingSearchTopBar(
            searchState = searchState,
            placeholder = stringResource(search_placeholder),
            onBackClick = onBackClick,
        )

        when (uiState.searchNickNameUsersUiState) {
            SearchUiState.Idle -> Unit
            SearchUiState.Loading -> Unit
            SearchUiState.Empty -> Unit
            SearchUiState.Success -> {
                if (uiState.suggestions.isNotEmpty()) {
                    items.forEach { item ->
                        Text(
                            text = item.nickname,
                            color = colors.txtSecondary,
                            style = typography.sm.medium14,
                            modifier = Modifier
                                .fillMaxWidth()
                                .noRippleClickable(
                                    onClick = { onSuggestionItemClick(item.userId) },
                                )
                                .padding(vertical = 12.dp)
                                .padding(start = 16.dp),
                        )
                    }
                } else {
                    SearchEmpty(
                        title = "검색 결과가 없습니다.",
                        subTitle = "다른 검색어를 입력해보세요",
                    )
                }
            }

            else -> Unit
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SearchInputScreenPreview() {
    SmashingAndroidTheme {
        SearchInputScreen(
            uiState = SearchContract.State(),
            items = persistentListOf(),
            searchState = rememberTextFieldState(),
            onBackClick = {},
            onSuggestionItemClick = {},
            modifier = Modifier
                .background(
                    color = colors.bgCanvas,
                )
        )
    }
}
