package com.smashing.app.presentation.write.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_fake_red
import com.smashing.app.R.string.asterisk_label
import com.smashing.app.core.common.type.ReviewRatingType
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ChipStyle.INACTIVE
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun ReviewScreen(
    nickname: String,
    selectedCardItems: ImmutableSet<ReviewRatingType>,
    onCardItemClick: (ReviewRatingType) -> Unit,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
    ) {

        SmashingDefaultTopBar(
            title = "후가 작성",
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

            ReviewRatingCard(
                selectedItems = selectedCardItems,
                onItemClick = onCardItemClick,
            )

            Text(
                text = "빠른 후기를 선택해 주세요",
                style = SmashingTheme.typography.md.medium16,
                color = SmashingTheme.colors.txtPrimary,
            )

            FlowRow(

            ) {
                SmashingChip(
                    text = "시간 약속을  지켜요",
                    style = INACTIVE,
                    icon = ImageVector.vectorResource(id = ic_fake_red),
                    onClick = {},
                )
            }

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
        )
    }
}
