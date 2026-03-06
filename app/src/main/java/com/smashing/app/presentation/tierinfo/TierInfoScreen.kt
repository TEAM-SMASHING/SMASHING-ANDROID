package com.smashing.app.presentation.tierinfo

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.designsystem.component.appicon.AppIcon
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme

@Composable
fun TierInfoRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TierInfoViewModel = hiltViewModel(),
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
    uiState: TierInfoContract.State,
    onClick: (TierInfoStyle) -> Unit,
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
                painter = painterResource(uiState.selectedTierInfoStyle.getImg()),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = uiState.selectedTierInfoStyle.tierName,
                style = SmashingTheme.typography.xl.semibold20,
                color = uiState.selectedTierInfoStyle.getTxtColor(),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                if (uiState.tierInfoDetail?.progressInfo != null) {
                    if (!uiState.tierInfoDetail.progressInfo.percentText.isNullOrBlank()) {
                        TierTag(
                            tagText = uiState.tierInfoDetail.progressInfo.percentText,
                        )
                    }
                    if (!uiState.tierInfoDetail.progressInfo.levelText.isNullOrBlank()) {
                        TierTag(
                            tagText = "실제 기준 ${uiState.tierInfoDetail.progressInfo.levelText}",
                        )
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
            ),
        ) {
            items(
                items = TierInfoStyle.entries,
            ) {
                SmashingChip(
                    text = it.tierName,
                    style = if (it != uiState.selectedTierInfoStyle) ChipStyle.INACTIVE else ChipStyle.ACTIVE,
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
            horizontalAlignment = Alignment.Start,
        ) {
            if (uiState.selectedTierInfoStyle != TierInfoStyle.CHALLENGER && uiState.selectedTierInfoStyle != TierInfoStyle.IRON) {
                Text(
                    text = "승급을 위해 아래의 기술들을 연마해보세요",
                    style = SmashingTheme.typography.md.semibold16,
                    color = SmashingTheme.colors.txtPrimary,
                )

                Spacer(modifier = Modifier.height(8.dp))
                if (uiState.tierInfoDetail != null) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            items = uiState.tierInfoDetail.skills,
                        ) { skill ->
                            CommentTag(
                                title = skill.name,
                                comment = skill.description,
                            )
                        }
                    }
                } else {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(80.dp))
                    AppIcon(
                        title = uiState.tierInfoDetail?.skills?.firstOrNull()?.name,
                        subtitle = uiState.tierInfoDetail?.skills?.firstOrNull()?.description,
                        isFilled = if (uiState.selectedTierInfoStyle == TierInfoStyle.CHALLENGER) true else false,
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
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun CommentTag(
    modifier: Modifier = Modifier,
    title: String? = "",
    comment: String? = "",
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
            text = title ?: "",
            style = SmashingTheme.typography.sm.semibold14,
            color = SmashingTheme.colors.txtPrimary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = comment ?: "",
            style = SmashingTheme.typography.xs.medium12,
            color = SmashingTheme.colors.txtSecondary,
        )
    }
}
