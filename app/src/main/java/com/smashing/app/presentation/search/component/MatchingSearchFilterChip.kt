package com.smashing.app.presentation.search.component

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
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.R.drawable.ic_close_sm
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.designsystem.theme.gray300
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.search.style.FilterStyle
import com.smashing.app.presentation.search.style.FilterStyle.DEFAULT
import com.smashing.app.presentation.search.style.FilterStyle.VARIANT
import com.smashing.app.presentation.search.style.FilterStyleData

@Composable
fun MatchingSearchFilterChip(
    style: FilterStyle,
    text: String,
    onFilterClick: () -> Unit,
    onFilterDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val filterStyle = style.getStyle()

    Row(
        modifier = modifier
            .background(
                color = filterStyle.bgColor,
                shape = RoundedCornerShape(999.dp),
            )
            .noRippleClickable(onClick = onFilterClick)
            .padding(vertical = 5.dp)
            .padding(start = 14.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = filterStyle.txtColor,
            style = typography.sm.medium14,
        )

        Spacer(modifier = Modifier.width(7.dp))

        Icon(
            imageVector = ImageVector.vectorResource(filterStyle.iconRes),
            contentDescription = null,
            modifier = if (style == VARIANT) {
                Modifier.noRippleClickable(onClick = onFilterDelete)
            } else {
                Modifier
            },
            tint = filterStyle.iconTint,
        )
    }
}

@ReadOnlyComposable
@Composable
private fun FilterStyle.getStyle(): FilterStyleData = when (this) {
    DEFAULT -> FilterStyleData(
        bgColor = SmashingTheme.colors.bgSurfacePressed,
        txtColor = SmashingTheme.colors.txtPrimary,
        iconRes = ic_arrow_down,
        iconTint = SmashingTheme.colors.iconPrimary,
    )

    VARIANT -> FilterStyleData(
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
                style = DEFAULT,
                text = "티어",
                onFilterClick = {},
                onFilterDelete = {},
            )

            Spacer(modifier = Modifier.height(10.dp))

            MatchingSearchFilterChip(
                style = VARIANT,
                text = "브론즈",
                onFilterClick = {},
                onFilterDelete = {},
            )
        }
    }
}
