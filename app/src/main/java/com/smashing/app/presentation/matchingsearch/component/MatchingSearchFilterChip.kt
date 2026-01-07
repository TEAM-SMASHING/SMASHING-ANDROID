package com.smashing.app.presentation.matchingsearch.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.gray300
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.matchingsearch.type.FilterType


@Composable
fun MatchingSearchFilterChip(
    type: FilterType,
    text: String,
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit = {},
) {
    val filterStyle = type.getStyle()

    Row(
        modifier = modifier
            .background(
                color = filterStyle.bgColor,
                shape = RoundedCornerShape(999.dp),
            )
            .noRippleClickable(onClick = onFilterClick)
            .padding(vertical = 5.dp)
            .padding(start = 14.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = filterStyle.txtColor,
        )

        Spacer(modifier = Modifier.width(7.dp))

        Icon(
            imageVector = ImageVector.vectorResource(filterStyle.iconRes),
            contentDescription = null,
            tint = filterStyle.iconTint,
        )
    }
}


@Preview(showBackground = false)
@Composable
private fun MatchingSearchFilterChipPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .background(color = gray300)
                .padding(5.dp),
        ) {
            MatchingSearchFilterChip(
                type = FilterType.DEFAULT,
                text = "티어",
            )

            Spacer(modifier = Modifier.height(10.dp))

            MatchingSearchFilterChip(
                type = FilterType.VARIANT,
                text = "브론즈",
            )
        }
    }
}
