package com.smashing.app.presentation.search.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.search_placeholder
import com.smashing.app.core.designsystem.component.topbar.SmashingSearchTopBar
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SearchInputRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchInputViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchInputScreen(
        items = uiState.suggestions,
        searchState = viewModel.searchInput,
        onBackClick = {},
        onSuggestionItemClick = {},
        modifier = modifier,
    )
}

@Composable
private fun SearchInputScreen(
    items: ImmutableList<SuggestionItem>,
    searchState: TextFieldState,
    onBackClick: () -> Unit,
    onSuggestionItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier,
    ){
        SmashingSearchTopBar(
            searchState = searchState,
            placeholder = stringResource(search_placeholder),
            onBackClick = onBackClick,
        )

        items.forEach { item ->
            Text(
                text = item.nickname,
                color = colors.txtSecondary,
                style = typography.sm.medium14,
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable(
                        onClick = onSuggestionItemClick,
                    )
                    .padding(vertical = 12.dp)
                    .padding(start = 16.dp),
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SearchInputScreenPreview() {
    SmashingAndroidTheme {
        SearchInputScreen(
            items = persistentListOf(
                SuggestionItem(
                    userId = "1",
                    nickname = "바나나"
                ),
                SuggestionItem(
                    userId = "2",
                    nickname = "바나1"
                ),
                SuggestionItem(
                    userId = "3",
                    nickname = "바나2"
                ),
                SuggestionItem(
                    userId = "4",
                    nickname = "바나3"
                ),
            ),
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

