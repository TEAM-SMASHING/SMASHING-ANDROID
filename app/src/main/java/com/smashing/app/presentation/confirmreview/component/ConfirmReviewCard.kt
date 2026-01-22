package com.smashing.app.presentation.confirmreview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.style.ChipStyle.DISABLED
import com.smashing.app.core.designsystem.theme.SmashingTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ConfirmReviewCard(
    iconId: Int,
    rating: String,
    reviewText: String?,
    nickname: String,
    tags: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    val hasReviewText = !reviewText.isNullOrBlank()
    val hasTags = tags.isNotEmpty()
    val isEmptyReview = !hasReviewText && !hasTags


    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ReviewHeader(
            iconId = iconId,
            rating = rating,
        )
        Spacer(modifier = Modifier.height(40.dp))


        if (hasReviewText) {
            Text(
                text = reviewText,
                style = SmashingTheme.typography.md.medium16,
                color = SmashingTheme.colors.txtSecondary,
                textAlign = TextAlign.Left,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )
        }

        if (hasReviewText) {
            Spacer(modifier = Modifier.height(40.dp))
        }

        if (isEmptyReview) {
            Box(
                modifier = Modifier
                    .weight(1f) // 빈 공간 꽉 채워서 중앙 정렬
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = nickname + "님이 구체적인 후기는 남기지 않았어요",
                    style = SmashingTheme.typography.md.regular16,
                    color = SmashingTheme.colors.txtTertiary,
                    textAlign = TextAlign.Center
                )
            }

        }
        if (hasTags) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                tags.forEach { keyword ->
                    SmashingChip(
                        text = keyword,
                        style = DISABLED,
                    )
                }
            }
        }
    }
}
@Composable
private fun ReviewHeader(
    iconId: Int,
    rating: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = (ImageVector.vectorResource(id = iconId)),
            modifier = Modifier.size(72.dp),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = rating,
            style = SmashingTheme.typography.xl.semibold20,
            color = SmashingTheme.colors.txtPrimary
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun ReviewCheckCardPreview() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        ConfirmReviewCard(
            rating = "최고예요",
            reviewText = "요즘은 두바이 쫀득쿠키가 유행이에요 맛있어요 근데 너무 비싸요 그치만 그 값을 해요 근데 비싸요  날씨가 너무 추워요 내일 눈이 와여 오늘은 새해에요 왜 벌써 2026인거죠 올해 태어난 사람은 2105년에 팔순이에요",
            tags = persistentListOf("승패를 깔끔하게 인정해요", "응답이 빨라요", "시간 약속을 잘 지켜요"),
            iconId = R.drawable.ic_crown,
            nickname = "밤이달이",
        )

        ConfirmReviewCard(
            rating = "최고예요",
            reviewText = null,
            tags = persistentListOf("승패를 깔끔하게 인정해요", "응답이 빨라요"),
            iconId = R.drawable.ic_crown,
            nickname = "밤이달이",
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun ReviewCheckCardPreview2() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(20.dp))
    {
        ConfirmReviewCard(
            rating = "좋아요",
            reviewText = "매너가 좋으셨습니다. 다음에 또 해요!",
            tags = persistentListOf(),
            iconId = R.drawable.ic_crown,
            nickname = "밤이달이",
        )

        ConfirmReviewCard(
            rating = "최고예요",
            reviewText = "",
            tags = persistentListOf(),
            iconId = R.drawable.ic_crown,
            nickname = "밤이달이",
        )
    }
}
