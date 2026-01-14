package com.smashing.app.presentation.write.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_check_button
import com.smashing.app.R.drawable.ic_check_button_default
import com.smashing.app.core.common.type.ReviewRatingType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun ReviewRatingCard(
    selectedItems: ImmutableSet<ReviewRatingType>,
    onItemClick: (ReviewRatingType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val ratingList = ReviewRatingType.entries

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                vertical = 16.dp,
                horizontal = 30.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ratingList.forEach { item ->
            val isSelected = item in selectedItems

            RatingItem(
                text = item.label,
                isSelected = isSelected,
                onClick = { onItemClick(item) },
                modifier = Modifier
            )

            if (item != ratingList.last()) {
                HorizontalDivider(
                    modifier = Modifier
                        .width(60.dp)
                        .align(Alignment.Top)
                        .padding(
                            top = 18.dp,
                        ),
                    color = SmashingTheme.colors.borderSecondary,
                    thickness = 1.dp,
                )
            }
        }
    }
}

@Composable
private fun RatingItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor =
        if (isSelected) SmashingTheme.colors.txtPrimary
        else SmashingTheme.colors.txtDisabled

    Column(
        modifier = modifier
            .noRippleClickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                if (isSelected) ic_check_button else ic_check_button_default
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    top = if (isSelected) 0.dp else 3.dp,
                    bottom = if (isSelected) 4.dp else 10.dp
                )
                .size(if (isSelected) 40.dp else 30.dp),
            tint = Color.Unspecified,
        )

        Text(
            text = text,
            style = SmashingTheme.typography.sm.regular14,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewRatingCardPreview() {
    SmashingAndroidTheme {
        ReviewRatingCard(
            selectedItems = persistentSetOf(
                ReviewRatingType.GOOD,
            ),
            onItemClick = {},
        )
    }
}
