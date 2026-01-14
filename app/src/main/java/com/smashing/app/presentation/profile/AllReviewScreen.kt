package com.smashing.app.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.ic_thumbs_down_lg
import com.smashing.app.R.drawable.ic_thumbs_up_double_lg
import com.smashing.app.R.drawable.ic_thumbs_up_lg
import com.smashing.app.R.string.receive_review
import com.smashing.app.R.string.good_manner_review
import com.smashing.app.R.string.on_time_review
import com.smashing.app.R.string.fair_play_review
import com.smashing.app.R.string.fast_response_review
import com.smashing.app.R.string.review
import com.smashing.app.R.string.satisfaction_review
import com.smashing.app.R.string.short_review
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ChipStyle.DISABLED
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.profile.Review
import com.smashing.app.presentation.profile.component.ReviewItem
import com.smashing.app.presentation.profile.navigation.AllReviewViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AllReviewRoute(
    modifier: Modifier = Modifier,
    viewModel: AllReviewViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllReviewScreen(
        modifier = modifier,
        uiState = uiState,
        onBackClick = navigateToBack,
        reviews = uiState.reviews,
    )
}


@Composable
private fun AllReviewScreen(
    uiState: ProfileContract.State,
    reviews: ImmutableList<Review>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            SmashingDefaultTopBar(
                title = stringResource(receive_review),
                topBarType = TopBarType.BACK,
                onClick = onBackClick,
            )
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = satisfaction_review),
                    style = SmashingTheme.typography.md.semibold16,
                    color = SmashingTheme.colors.txtPrimary
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (uiState.reviewRate.best > 0) {
                        SmashingChip(
                            text = uiState.reviewRate.best.toString(),
                            style = DISABLED,
                            icon = ImageVector.vectorResource(id = ic_thumbs_up_double_lg),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        )
                    }

                    if (uiState.reviewRate.good > 0)
                        SmashingChip(
                            text = uiState.reviewRate.good.toString(),
                            style = DISABLED,
                            icon = ImageVector.vectorResource(id = ic_thumbs_up_lg),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        )

                    if (uiState.reviewRate.bad > 0) {
                        SmashingChip(
                            text = uiState.reviewRate.bad.toString(),
                            style = DISABLED,
                            icon = ImageVector.vectorResource(id = ic_thumbs_down_lg),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        )
                    }
                }
            }
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(id = short_review),
                    style = SmashingTheme.typography.md.semibold16,
                    color = SmashingTheme.colors.txtPrimary,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (uiState.tagCount.onTime > 0) {
                        SmashingChip(
                            text = "${stringResource(id = on_time_review)} ${uiState.tagCount.onTime}",
                            style = DISABLED,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        )
                    }

                    if (uiState.tagCount.goodManner > 0) {
                        SmashingChip(
                            text = "${stringResource(id = good_manner_review)} ${uiState.tagCount.goodManner}",
                            style = DISABLED,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        )
                    }
                    if (uiState.tagCount.fairPlay > 0) {
                        SmashingChip(
                            text = "${stringResource(id = fair_play_review)} ${uiState.tagCount.fairPlay}",
                            style = DISABLED,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        )
                    }
                    if (uiState.tagCount.fastResponse > 0) {
                        SmashingChip(
                            text = "${stringResource(id = fast_response_review)} ${uiState.tagCount.fastResponse}",
                            style = DISABLED,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        )
                    }
                }
            }
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = stringResource(id = review),
                    style = SmashingTheme.typography.md.semibold16,
                    color = SmashingTheme.colors.txtPrimary
                )
                Column {
                    reviews.forEachIndexed { index, review ->
                        ReviewItem(review = review, userId = "userId$index")

                        if (index < reviews.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                thickness = 1.dp,
                                color = SmashingTheme.colors.borderPrimary,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun AllReviewScreenPreview() {
    SmashingAndroidTheme {
        AllReviewScreen(
            uiState = ProfileContract.State(),
            onBackClick = {},
            reviews = ProfileContract.State().reviews,
        )
    }
}
