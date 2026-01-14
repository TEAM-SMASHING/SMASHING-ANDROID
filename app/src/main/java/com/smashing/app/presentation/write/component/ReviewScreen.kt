package com.smashing.app.presentation.write.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
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
import com.smashing.app.core.common.type.ReviewRatingType
import com.smashing.app.core.common.type.ReviewTagType
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.textfield.SmashingAreaTextField
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

private const val REVIEW_RATIO = 328 / 156f

@Composable
fun ReviewScreen(
    nickname: String,
    textFieldState: TextFieldState,
    selectedCardItems: ImmutableSet<ReviewRatingType>,
    onCardItemClick: (ReviewRatingType) -> Unit,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
    ) {

        SmashingDefaultTopBar(
            title = "후기 작성",
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
            modifier = modifier,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
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
                selectedItems = selectedCardItems,
                onItemClick = onCardItemClick,
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
                    SmashingChip(
                        text = it.tagLabel,
                        style = ChipStyle.INACTIVE,
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

            Spacer(Modifier.weight(1f))

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = "완료",
                onClick = {},
                isEnabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 48.dp,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewScreenPreview() {
    SmashingAndroidTheme {
        ReviewScreen(
            nickname = "닉네임",
            scrollState = ScrollState(0),
            onBackClick = {},
            selectedCardItems = persistentSetOf(ReviewRatingType.BAD, ReviewRatingType.GOOD),
            onCardItemClick = {},
            textFieldState = TextFieldState(),
            isButtonEnabled = false,
            onDoneClick = {},
        )
    }
}
