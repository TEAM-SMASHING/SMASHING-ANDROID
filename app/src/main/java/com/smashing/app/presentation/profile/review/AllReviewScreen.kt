package com.smashing.app.presentation.profile.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.ic_thumbs_down_lg
import com.smashing.app.R.drawable.ic_thumbs_up_double_lg
import com.smashing.app.R.drawable.ic_thumbs_up_lg
import com.smashing.app.R.drawable.img_app_icon
import com.smashing.app.R.string.fair_play_review
import com.smashing.app.R.string.fast_response_review
import com.smashing.app.R.string.good_manner_review
import com.smashing.app.R.string.on_time_review
import com.smashing.app.R.string.receive_review
import com.smashing.app.R.string.review
import com.smashing.app.R.string.satisfaction_review
import com.smashing.app.R.string.short_review
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.onBottomReached
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.presentation.profile.component.ReviewItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AllReviewRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AllReviewViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllReviewScreen(
        modifier = modifier,
        uiState = uiState,
        reviews = uiState.gameReview,
        onLoadMoreReviewList = { viewModel.fetchMyReviews(isInit = false) },
        onBackClick = navigateUp,
    )
}


@Composable
private fun AllReviewScreen(
    uiState: ReviewContract.State,
    reviews: ImmutableList<GameReview>,
    onLoadMoreReviewList: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val currentIsLoading = uiState.reviewUiState is ReviewContract.ReviewUiState.Loading

    val hasShortReview = uiState.gameReviewResult.run {
        onTimeCount > 0 || goodMannerCount > 0 || fairPlayCount > 0 || fastResponseCount > 0
    }

    Column(
        modifier = modifier.run {
            fillMaxSize()
                .background(color = SmashingTheme.colors.bgCanvas)
        },
    ) {
        SmashingDefaultTopBar(
            modifier = Modifier.statusBarsPadding(),
            title = stringResource(receive_review),
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        if (uiState.isReviewEmpty) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = img_app_icon),
                    contentDescription = null,
                )

                Spacer(Modifier.padding(top = 12.dp))

                Text(
                    text = "아직 받은 후기가 없어요",
                    style = SmashingTheme.typography.md.medium16,
                    color = SmashingTheme.colors.txtSecondary,
                )
            }
        } else {
            lazyListState.onBottomReached(
                threshold = 3,
                onLoadMore = onLoadMoreReviewList,
                isLoading = currentIsLoading,
            )

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = SmashingTheme.colors.bgCanvas)
                    .navigationBarsPadding(),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = satisfaction_review),
                            style = SmashingTheme.typography.md.semibold16,
                            color = SmashingTheme.colors.txtPrimary,
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            if (uiState.gameReviewResult.bestCount > 0) {
                                SmashingChip(
                                    text = uiState.gameReviewResult.bestCount.toString(),
                                    style = ChipStyle.DISABLED,
                                    icon = ImageVector.vectorResource(id = ic_thumbs_up_double_lg),
                                )
                            }
                            if (uiState.gameReviewResult.goodCount > 0) {
                                SmashingChip(
                                    text = uiState.gameReviewResult.goodCount.toString(),
                                    style = ChipStyle.DISABLED,
                                    icon = ImageVector.vectorResource(id = ic_thumbs_up_lg),
                                )
                            }
                            if (uiState.gameReviewResult.badCount > 0) {
                                SmashingChip(
                                    text = uiState.gameReviewResult.badCount.toString(),
                                    style = ChipStyle.DISABLED,
                                    icon = ImageVector.vectorResource(id = ic_thumbs_down_lg),
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = stringResource(id = short_review),
                            style = SmashingTheme.typography.md.semibold16,
                            color = SmashingTheme.colors.txtPrimary,
                        )

                        if (hasShortReview) {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                if (uiState.gameReviewResult.onTimeCount > 0) {
                                    SmashingChip(
                                        text = "${stringResource(id = on_time_review)} ${uiState.gameReviewResult.onTimeCount}",
                                        style = ChipStyle.DISABLED,
                                    )
                                }
                                if (uiState.gameReviewResult.goodMannerCount > 0) {
                                    SmashingChip(
                                        text = "${stringResource(id = good_manner_review)} ${uiState.gameReviewResult.goodMannerCount}",
                                        style = ChipStyle.DISABLED,
                                    )
                                }
                                if (uiState.gameReviewResult.fairPlayCount > 0) {
                                    SmashingChip(
                                        text = "${stringResource(id = fair_play_review)} ${uiState.gameReviewResult.fairPlayCount}",
                                        style = ChipStyle.DISABLED,
                                    )
                                }
                                if (uiState.gameReviewResult.fastResponseCount > 0) {
                                    SmashingChip(
                                        text = "${stringResource(id = fast_response_review)} ${uiState.gameReviewResult.fastResponseCount}",
                                        style = ChipStyle.DISABLED,
                                    )
                                }
                            }
                        } else {
                            ReviewEmptyPlaceholder(
                                text = "아직 받은 빠른 후기가 없어요",
                                modifier = Modifier.padding(vertical = 20.dp)
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }

                item {
                    Text(
                        text = stringResource(id = review),
                        style = SmashingTheme.typography.md.semibold16,
                        color = SmashingTheme.colors.txtPrimary,
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                if (reviews.isNotEmpty()) {
                    itemsIndexed(reviews) { index, review ->
                        ReviewItem(
                            review = review,
                        )

                        if (index < reviews.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                thickness = 1.dp,
                                color = SmashingTheme.colors.borderPrimary,
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                } else {
                    item {
                        ReviewEmptyPlaceholder(
                            text = "아직 받은 후기가 없어요",
                            modifier = Modifier.padding(vertical = 40.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewEmptyPlaceholder(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = SmashingTheme.typography.sm.regular14,
            color = SmashingTheme.colors.txtPrimary,
            textAlign = TextAlign.Center,
        )
    }
}


@Preview
@Composable
private fun PreviewNoTextReview() {
    SmashingAndroidTheme {
        val mockResult = GameReviewResult(
            bestCount = 10,
            onTimeCount = 5,
            goodMannerCount = 3,
            fairPlayCount = 2,
            fastResponseCount = 1
        )

        val mockState = ReviewContract.State(
            gameReviewResult = mockResult,
            reviewUiState = ReviewContract.ReviewUiState.Success,
        )

        AllReviewScreen(
            uiState = mockState,
            reviews = persistentListOf(),
            onLoadMoreReviewList = {},
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewNoFastAndTextReview() {
    SmashingAndroidTheme {
        val mockResult = GameReviewResult(
            bestCount = 5,
            goodCount = 2,
            badCount = 1,
            onTimeCount = 0,
            goodMannerCount = 0,
            fairPlayCount = 0,
            fastResponseCount = 0,
        )

        val mockState = ReviewContract.State(
            gameReviewResult = mockResult,
            reviewUiState = ReviewContract.ReviewUiState.Success,
        )

        AllReviewScreen(
            uiState = mockState,
            reviews = persistentListOf(),
            onLoadMoreReviewList = {},
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewAllEmpty() {
    SmashingAndroidTheme {
        val mockState = ReviewContract.State(
            reviewUiState = ReviewContract.ReviewUiState.Success,
        )

        AllReviewScreen(
            uiState = mockState,
            reviews = persistentListOf(),
            onLoadMoreReviewList = {},
            onBackClick = {}
        )
    }
}
