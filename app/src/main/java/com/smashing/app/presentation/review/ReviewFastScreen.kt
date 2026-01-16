package com.smashing.app.presentation.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.string.review
import com.smashing.app.core.common.type.ReviewRatingType
import com.smashing.app.core.common.type.ReviewTagType
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.review.component.ConfirmReviewCard
import com.smashing.app.presentation.write.confirm.ConfirmContract
import com.smashing.app.presentation.write.model.MatchPlayer
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList


@Composable
fun ReviewFastRoute(
    navigateUp: () -> Unit,
    navigateToNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewFastViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReviewFastScreen(
        uiState = uiState,
        modifier = modifier,
        onConfirmClick = navigateToNext
    )
}

@Composable
private fun ReviewFastScreen(
    uiState: ConfirmContract.State,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ratingType = when (uiState.rating) {
        "최고에요", "BEST" -> ReviewRatingType.BEST
        "좋아요", "GOOD" -> ReviewRatingType.GOOD
        "별로에요", "아쉬워요", "BAD" -> ReviewRatingType.BAD
        else -> throw IllegalArgumentException("Unknown rating type: ${uiState.rating}")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SmashingTheme.colors.bgCanvas,
            )
            .padding(horizontal = 16.dp)
            .systemBarsPadding()
    ) {
        SmashingDefaultTopBar(
            title = stringResource(review),
            topBarType = TopBarType.DEFAULT,
            onClick = null,
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(
                text = "${uiState.submitter.name}님이\n보낸 후기가 도착했어요",
                color = SmashingTheme.colors.txtPrimary,
                style = SmashingTheme.typography.xl.semibold20,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 24.dp))

            ConfirmReviewCard(
                rating = uiState.rating,
                reviewText = uiState.reviewText,
                tag = uiState.selectedTagTypes.map { it.tagLabel }
                    .toImmutableList(),
                iconId = ratingType.iconResId,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(68.dp))

            SmashingButton(
                text = "확인",
                buttonStyle = ButtonStyle.PRIMARY,
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
            )
        }
    }
}

val ReviewRatingType.iconResId: Int
    get() = when (this) {
        ReviewRatingType.BEST -> R.drawable.ic_thumbs_up_double_lg
        ReviewRatingType.GOOD -> R.drawable.ic_thumbs_up_lg
        ReviewRatingType.BAD -> R.drawable.ic_thumbs_down_lg
    }


@Preview(showBackground = true, heightDp = 800)
@Composable
private fun ConfirmFastReviewScreenPreview_Full() {
    SmashingAndroidTheme {
        ReviewFastScreen(
            uiState = ConfirmContract.State(
                submitter = MatchPlayer(userId = "1", name = "밤이달이"),
                rating = "최고에요",
                reviewText = "매너가 너무 좋으시고 실력도 뛰어나세요! 즐겜했습니다. 다음에 또 매칭 잡혔으면 좋겠어요.",
                selectedTagTypes = persistentSetOf(
                    ReviewTagType.GOOD_MANNER,
                    ReviewTagType.FAST_RESPONSE,
                )
            ),
            onConfirmClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun ConfirmFastReviewScreenPreview_Empty() {
    SmashingAndroidTheme {
        ReviewFastScreen(
            uiState = ConfirmContract.State(
                submitter = MatchPlayer(userId = "2", name = "스매싱초보"),
                rating = "좋아요",
                reviewText = "",
                selectedTagTypes = persistentSetOf()
            ),
            onConfirmClick = {}
        )
    }
}
