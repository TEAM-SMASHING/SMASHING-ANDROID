package com.smashing.app.presentation.profile.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_thumbs_down_lg
import com.smashing.app.R.drawable.ic_thumbs_up_double_lg
import com.smashing.app.R.drawable.ic_thumbs_up_lg
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.style.ChipStyle.DISABLED
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.extension.toFriendlyString
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.data.model.Review
import com.smashing.app.presentation.profile.ProfileContract
import kotlinx.collections.immutable.ImmutableList


@Composable
fun ProfileReviewCard(
    modifier: Modifier = Modifier,
    reviews: ImmutableList<Review>,
    excellentCount: Int,
    goodCount: Int,
    badCount: Int,
    onViewAllClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SmashingTheme.colors.bgSurface)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "받은 후기",
                style = SmashingTheme.typography.md.semibold16,
                color = SmashingTheme.colors.txtPrimary,
            )
            Text(
                text = "모두 보기",
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtSecondary,
                modifier = Modifier.noRippleClickable(onViewAllClick),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SmashingChip(
                text = excellentCount.toString(),
                style = DISABLED,
                icon = ImageVector.vectorResource(id = ic_thumbs_up_double_lg),
            )

            SmashingChip(
                text = goodCount.toString(),
                style = DISABLED,
                icon = ImageVector.vectorResource(id = ic_thumbs_up_lg),
            )

            SmashingChip(
                text = badCount.toString(),
                style = DISABLED,
                icon = ImageVector.vectorResource(id = ic_thumbs_down_lg),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            reviews.forEachIndexed { index, review ->
                ReviewItem(review = review, userId = "userId$index")

                if (index < reviews.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        thickness = 1.dp,
                        color = SmashingTheme.colors.txtTertiary,
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewItem(
    review: Review,
    userId: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        UrlImage(
            url = ProfileImageProvider.getTempUrl(userId),
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = review.opponentNickname,
                    style = SmashingTheme.typography.sm.semibold14,
                    color = SmashingTheme.colors.txtPrimary,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = review.confirmedAt.toFriendlyString(),
                    style = SmashingTheme.typography.xs.medium12,
                    color = SmashingTheme.colors.txtTertiary,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.content ?: "",
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtSecondary,
            )
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
