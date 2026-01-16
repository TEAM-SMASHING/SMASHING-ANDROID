package com.smashing.app.presentation.home.tierinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.profile.ActiveUserProfile
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.home.HomeContract
import com.smashing.app.presentation.home.HomeUiState
import com.smashing.app.presentation.home.HomeViewModel
import com.smashing.app.presentation.home.type.TierInfo
import com.smashing.app.presentation.home.type.toTierInfo
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TierInfoRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TierInfoScreen(
        uiState = uiState,
        onClick = viewModel::updateTierInfo,
        onBack = navigateUp,
        modifier = modifier,
    )
}

@Composable
private fun TierInfoScreen(
    uiState: HomeContract.State,
    onClick: (TierInfo) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SmashingTheme.colors.bgCanvas
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SmashingDefaultTopBar(
            title = "티어 설명",
            topBarType = TopBarType.CLOSE,
            onClick = onBack,
        )

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(uiState.selectedTierInfo.getImg()),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = uiState.selectedTierInfo.tierName,
                style = SmashingTheme.typography.xl.semibold20,
                color = uiState.selectedTierInfo.getTxtColor(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //TODO 더미 추가 후 수정 예정
                TierTag(
                    tagText = "티어 설명",
                )
                TierTag(
                    tagText = "티어 설명",
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
            ),
        ) {
            items(
                items = TierInfo.entries,
                key = { it.tierName },
            ) {
                SmashingChip(
                    text = it.tierName,
                    style = if (it != uiState.selectedTierInfo) ChipStyle.INACTIVE else ChipStyle.ACTIVE,
                    onClick = { onClick(it) },
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
        ) {
            Text(
                text = "승급을 위해 아래의 기술들을 연마해보세요",
                style = SmashingTheme.typography.sm.semibold14,
                color = SmashingTheme.colors.txtPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                //TODO 더미 추가 후 수정 예정
                items(
                    items = listOf("", "", ""),
                ) {
                    CommentTag(
                        title = "test",
                        comment = "test",
                    )
                }
            }
        }
    }
}

@Composable
private fun TierTag(
    tagText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = tagText,
        style = SmashingTheme.typography.sm.medium14,
        color = SmashingTheme.colors.txtPrimary,
        modifier = modifier
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                8.dp,
            ),
    )
}

@Composable
private fun CommentTag(
    title: String,
    comment: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            style = SmashingTheme.typography.sm.semibold14,
            color = SmashingTheme.colors.txtPrimary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = comment,
            style = SmashingTheme.typography.xs.medium12,
            color = SmashingTheme.colors.txtSecondary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TierInfoScreenPreview() {
    SmashingAndroidTheme {
        val dummyState = HomeContract.State(
            loadState = HomeUiState.Success,
            activeUserProfile = ActiveUserProfile(
                nickname = "테스트유저",
                region = "서울",
                profileId = "0USP111222333",
                sportType = SportType.TENNIS,
                tierType = TierType.GOLD_1,
                lp = 123,
                minLp = 100,
                maxLp = 500,
                wins = 10,
                losses = 7,
            ),
            topRankerList = persistentListOf(),
            matchingCardList = persistentListOf(),
            matchedUser = null,
            isNotice = false,
            selectedTierInfo = TierType.GOLD_1.toTierInfo(),
        )

        TierInfoScreen(
            uiState = dummyState,
            modifier = Modifier,
            onClick = {},
            onBack = {},
        )
    }
}