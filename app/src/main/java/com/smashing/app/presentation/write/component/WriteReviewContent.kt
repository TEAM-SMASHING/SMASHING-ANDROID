package com.smashing.app.presentation.write.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.string.asterisk_label
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.textfield.SmashingAreaTextField
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

private const val REVIEW_RATIO = 328 / 156f

@Composable
fun WriteReviewContent(
    nickname: String,
    textFieldState: TextFieldState,
    selectedReviewRatingTypes: ImmutableSet<ReviewRatingType>,
    selectedReviewTagTypes: ImmutableSet<ReviewTagType>,
    onReviewRatingClick: (ReviewRatingType) -> Unit,
    onReviewTagClick: (ReviewTagType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "${nickname}과의\n경기는 어떠셨나요?",
            style = SmashingTheme.typography.xl.semibold20,
            color = SmashingTheme.colors.txtPrimary,
            modifier = Modifier
                .padding(bottom = 24.dp),
        )

        Text(
            text = buildAnnotatedString {
                append("만족도를 선택해주세요 ")

                withStyle(
                    style = SpanStyle(color = SmashingTheme.colors.txtRed),
                ) {
                    append(stringResource(asterisk_label))
                }
            },
            style = SmashingTheme.typography.md.medium16,
            color = SmashingTheme.colors.txtPrimary,
        )

        Spacer(Modifier.height(8.dp))

        ReviewRatingCard(
            selectedItems = selectedReviewRatingTypes,
            onItemClick = onReviewRatingClick,
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = "빠른 후기를 선택해 주세요",
            style = SmashingTheme.typography.md.medium16,
            color = SmashingTheme.colors.txtPrimary,
        )

        FlowRow(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 28.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            ReviewTagType.entries.forEach {
                val isSelected = selectedReviewTagTypes.contains(it)

                SmashingChip(
                    text = it.tagLabel,
                    style = if (isSelected) ChipStyle.ACTIVE else ChipStyle.INACTIVE,
                    onClick = { onReviewTagClick(it) },
                )
            }
        }

        Text(
            text = "따뜻한 후기를 보내주세요!",
            style = SmashingTheme.typography.md.medium16,
            color = SmashingTheme.colors.txtPrimary,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        SmashingAreaTextField(
            state = textFieldState,
            placeholder = "매칭 후기를 작성해주세요",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(REVIEW_RATIO),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteReviewContentPreview() {
    SmashingAndroidTheme {
        WriteReviewContent(
            nickname = "닉네임",
            selectedReviewRatingTypes = persistentSetOf(
                ReviewRatingType.BAD,
                ReviewRatingType.GOOD
            ),
            selectedReviewTagTypes = persistentSetOf(ReviewTagType.ON_TIME),
            textFieldState = TextFieldState(),
            onReviewRatingClick = {},
            onReviewTagClick = {},
        )
    }
}
