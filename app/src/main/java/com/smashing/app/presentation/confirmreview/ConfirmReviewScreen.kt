package com.smashing.app.presentation.confirmreview

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.ic_thumbs_down_lg
import com.smashing.app.R.drawable.ic_thumbs_up_double_lg
import com.smashing.app.R.drawable.ic_thumbs_up_lg
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.presentation.confirmreview.component.ConfirmReviewCard
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ConfirmReviewRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConfirmReviewViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConfirmReviewScreen(
        uiState = uiState,
        onBackClick = navigateUp,
        onConfirmClick = navigateUp,
        modifier = modifier,
    )
}

@Composable
private fun ConfirmReviewScreen(
    uiState: ConfirmReviewContract.State,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .systemBarsPadding(),
    ) {
        SmashingDefaultTopBar(
            title = "후기",
            topBarType = TopBarType.DEFAULT,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "${uiState.nickname}님이\n보낸 후기가 도착했어요",
                style = SmashingTheme.typography.xl.semibold20,
                color = SmashingTheme.colors.txtPrimary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            ConfirmReviewCard(
                iconId = uiState.reviewRatingType.getReviewRatingTypeIcon(),
                rating = uiState.reviewRatingType.label,
                reviewText = uiState.reviewText,
                nickname = uiState.nickname,
                tags = uiState.tags,
                modifier = Modifier.weight(1f),
            )

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY,
                text = "확인",
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 68.dp,
                        bottom = 48.dp,
                    ),
            )
        }
    }
}

private fun ReviewRatingType.getReviewRatingTypeIcon() = when (this) {
    ReviewRatingType.BEST -> ic_thumbs_up_double_lg
    ReviewRatingType.GOOD -> ic_thumbs_up_lg
    ReviewRatingType.BAD -> ic_thumbs_down_lg
}


@Preview(showBackground = true)
@Composable
private fun ConfirmReviewScreenPreview() {
    SmashingAndroidTheme {
        ConfirmReviewScreen(
            uiState = ConfirmReviewContract.State(
                nickname = "밤이달이",
                reviewRatingType = ReviewRatingType.BEST,
                reviewText = "매너가 좋으셨습니다. 다음에 또 해요!",
                tags = persistentListOf("시간 약속을 잘 지켜요", "경기 매너가 좋아요"),
            ),
            onBackClick = {},
            onConfirmClick = {},
        )
    }
}
