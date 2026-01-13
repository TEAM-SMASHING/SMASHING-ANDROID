package com.smashing.app.presentation.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.matching.type.MatchingType

@Composable
fun MatchingTabBar(
    selectedType: MatchingType,
    onTabClick: (MatchingType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabItems = MatchingType.entries

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(16.dp),
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        tabItems.forEach { matchingType ->
            val isSelected = selectedType == matchingType
            val backgroundColor =
                if (isSelected) SmashingTheme.colors.bgSelected else Color.Transparent
            val textColor =
                if (isSelected) SmashingTheme.colors.txtPrimaryReverse else SmashingTheme.colors.txtDisabled

            Text(
                text = stringResource(matchingType.labelRes),
                color = textColor,
                style = SmashingTheme.typography.sm.semibold14,
                modifier = Modifier
                    .padding(
                        vertical = 4.dp,
                    )
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(
                        vertical = 10.dp,
                        horizontal = 26.dp,
                    )
                    .noRippleClickable(
                        onClick = { onTabClick(matchingType) },
                        isEnabled = !isSelected,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingTabBarPreview() {
    SmashingAndroidTheme {
        MatchingTabBar(
            modifier = Modifier
                .fillMaxWidth(),
            onTabClick = {},
            selectedType = MatchingType.RECEIVE,
        )
    }
}
