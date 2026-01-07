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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.R.drawable.ic_close_sm
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.designsystem.theme.gray300
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.matchingsearch.type.FilterStyle
import com.smashing.app.presentation.matchingsearch.type.FilterType


@Composable
fun MatchingSearchFilterChip(
    type: FilterType,
    text: String,
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .background(
                color = type.style().bgColor,
                shape = RoundedCornerShape(999.dp),
            )
            .noRippleClickable(onClick = onFilterClick)
            .padding(vertical = 4.dp)
            .padding(start = 14.dp, end = 8.dp),
    ) {
        Text(
            text = text,
            color = type.style().txtColor,
        )

        Spacer(modifier = Modifier.width(7.dp))

        Icon(
            painter = painterResource(type.style().iconRes),
            contentDescription = null,
            tint = type.style().iconTint,
        )
    }
}


@Composable
private fun FilterType.style(): FilterStyle = when (this) {
    FilterType.DEFAULT -> FilterStyle(
        bgColor = SmashingTheme.colors.bgSurfacePressed,
        txtColor = SmashingTheme.colors.txtPrimary,
        iconRes = ic_arrow_down,
        iconTint = SmashingTheme.colors.iconPrimary,
    )

    FilterType.VARIANT -> FilterStyle(
        bgColor = SmashingTheme.colors.bgSelected,
        txtColor = SmashingTheme.colors.txtPrimaryReverse,
        iconRes = ic_close_sm,
        iconTint = SmashingTheme.colors.iconTertiary,
    )
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
