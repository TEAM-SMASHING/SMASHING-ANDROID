package com.smashing.app.presentation.matching

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.drawable.img_app_icon
import com.smashing.app.R.string.matching_confirm_empty
import com.smashing.app.R.string.matching_receive_empty
import com.smashing.app.R.string.matching_send_empty
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.matching.component.MatchingTabBar
import com.smashing.app.presentation.matching.type.MatchingType

@Composable
fun MatchingRoute(
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MatchingScreen(
        uiState = uiState,
        onTabClick = viewModel::updateMatchingType,
        modifier = modifier,
    )
}

@Composable
private fun MatchingScreen(
    uiState: MatchingContract.State,
    onTabClick: (MatchingType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val emptyTitle = stringResource(
        when (uiState.selectedType) {
            MatchingType.SEND -> matching_send_empty
            MatchingType.RECEIVE -> matching_receive_empty
            MatchingType.ACCEPTED -> matching_confirm_empty
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.matching_manage),
            style = SmashingTheme.typography.md.semibold16,
            color = SmashingTheme.colors.txtPrimary,
            modifier = Modifier
                .padding(
                    vertical = 21.dp,
                ),
        )

        MatchingTabBar(
            selectedType = uiState.selectedType,
            onTabClick = onTabClick,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        if (uiState.loadState is MatchingUiState.Empty) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.weight(171 / 252f))

                Image(
                    painter = painterResource(img_app_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)
                        .padding(bottom = 16.dp),
                )

                Text(
                    text = emptyTitle,
                    style = SmashingTheme.typography.lg.semibold18,
                    color = SmashingTheme.colors.txtSecondary,
                )

                Text(
                    text = stringResource(R.string.matching_empty_description),
                    style = SmashingTheme.typography.sm.medium14,
                    color = SmashingTheme.colors.txtTertiary,
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        if (uiState.loadState is MatchingUiState.Success) {
            MatchingList(
                uiState = uiState,
                gridState = gridState,
            )
        }
    }
}

@Composable
private fun MatchingList(
    uiState: MatchingContract.State,
    gridState: LazyGridState,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        contentPadding = PaddingValues(bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        modifier = modifier,
    ) {
        when (uiState.selectedType) {
            MatchingType.SEND -> items(uiState.sendList) {
                MatchingCard(
                    cardState = MatchingCardState.Send(
                        userId = it.userId,
                        nickname = it.nickname,
                        genderType = it.genderType,
                        tierType = it.tierType,
                        onProfileClick = {},
                        onCloseClick = {},
                        winCount = it.winCount,
                        loseCount = it.loseCount,
                        reviewCount = it.reviewCount,
                    ),
                )
            }

            MatchingType.RECEIVE -> items(uiState.receiveList) {
                MatchingCard(
                    cardState = MatchingCardState.Receive(
                        userId = it.userId,
                        nickname = it.nickname,
                        genderType = it.genderType,
                        tierType = it.tierType,
                        winCount = it.winCount,
                        loseCount = it.loseCount,
                        reviewCount = it.reviewCount,
                        onProfileClick = {},
                        onSkipClick = {},
                        onAcceptClick = {},
                    ),
                )
            }

            MatchingType.ACCEPTED -> items(uiState.acceptedList) {
                MatchingCard(
                    cardState = MatchingCardState.Confirm(
                        userId = it.userId,
                        nickname = it.nickname,
                        genderType = it.genderType,
                        tierType = it.tierType,
                        onProfileClick = {},
                        onConfirmClick = {},
                        onKakaoLinkClick = {},
                        onCloseClick = {},
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingScreenPreview() {
    SmashingAndroidTheme {

        MatchingScreen(
            uiState = MatchingContract.State(),
            onTabClick = {},
            modifier = Modifier
                .background(Color.Black),
        )
    }
}
