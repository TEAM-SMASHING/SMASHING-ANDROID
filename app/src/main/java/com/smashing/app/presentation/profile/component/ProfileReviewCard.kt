package com.smashing.app.presentation.profile.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_thumbs_down_lg
import com.smashing.app.R.drawable.ic_thumbs_up_double_lg
import com.smashing.app.R.drawable.ic_thumbs_up_lg
import com.smashing.app.R.string.all_review
import com.smashing.app.R.string.receive_review
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.style.ChipStyle.DISABLED
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.formatCount
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.model.profile.Review
import com.smashing.app.presentation.profile.ProfileContract
import kotlinx.collections.immutable.ImmutableList

private const val REVIEW_ITEM_COUNT = 3

@Composable
fun ProfileReviewCard(
    reviews: ImmutableList<Review>,
    excellentCount: Int,
    goodCount: Int,
    badCount: Int,
    modifier: Modifier = Modifier,
    onViewAllReviewClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SmashingTheme.colors.bgSurface)
            .padding(vertical = 20.dp, horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = receive_review),
                style = SmashingTheme.typography.md.semibold16,
                color = SmashingTheme.colors.txtPrimary,
            )
            Text(
                text = stringResource(id = all_review),
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtTertiary,
                modifier = Modifier.noRippleClickable(onViewAllReviewClick),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (excellentCount > 0) {
                SmashingChip(
                    text = excellentCount.formatCount(),
                    style = DISABLED,
                    icon = ImageVector.vectorResource(id = ic_thumbs_up_double_lg),
                )
            }

            if (goodCount > 0) {
                SmashingChip(
                    text = goodCount.formatCount(),
                    style = DISABLED,
                    icon = ImageVector.vectorResource(id = ic_thumbs_up_lg),
                )
            }

            if (badCount > 0) {
                SmashingChip(
                    text = badCount.formatCount(),
                    style = DISABLED,
                    icon = ImageVector.vectorResource(id = ic_thumbs_down_lg),
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            reviews.take(3).forEachIndexed { index, review ->
                ReviewItem(
                    review = review,
                )

                if (index < REVIEW_ITEM_COUNT - 1) {
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

@Preview
@Composable
private fun ProfileReviewCardPreview() {
    SmashingAndroidTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ProfileReviewCard(
                reviews = ProfileContract.State().reviews,
                excellentCount = ProfileContract.State().reviewRate.best,
                goodCount = ProfileContract.State().reviewRate.good,
                badCount = ProfileContract.State().reviewRate.bad,
            )
        }
    }
}
