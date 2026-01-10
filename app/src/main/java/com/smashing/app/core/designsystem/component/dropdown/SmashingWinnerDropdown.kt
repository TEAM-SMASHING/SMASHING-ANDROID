package com.smashing.app.core.designsystem.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.core.extension.noRippleClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography

@Composable
fun SmashingWinnerDropdown(
    selectedItem: String?,
    items: List<String>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "승자 선택",
    isDivide: Boolean = true,
) {
    var isExpanded by remember { mutableStateOf(false) }

    var triggerWidth by remember { mutableStateOf(0.dp) }
    var triggerHeight by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    triggerWidth = with(density) { coordinates.size.width.toDp() }
                    triggerHeight = with(density) { coordinates.size.height.toDp() }
                }
                .defaultMinSize(minWidth = 123.dp)
                .background(
                    color = colors.bgSurface,
                    shape = RoundedCornerShape(8.dp)
                )
                .noRippleClickable(
                    onClick = { isExpanded = !isExpanded }
                )
                .padding(vertical = 8.dp)
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = selectedItem ?: placeholder,
                style = typography.sm.medium14,
                color = if (selectedItem == null) {
                    colors.txtDisabled
                } else {
                    colors.txtSecondary
                },
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (selectedItem == null) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_down),
                    contentDescription = "Dropdown Arrow",
                    tint = colors.txtDisabled,
                )
            }
        }
        if (items.isNotEmpty()) {
            SmashingDropdownMenu(
                items = items.map {
                    DropdownItem.Normal(it)
                },
                isExpanded = isExpanded,
                triggerWidth = triggerWidth,
                triggerHeight = triggerHeight,
                density = density,
                onItemClick = onClick,
                onDismiss = { isExpanded = false },
                isDivided = isDivide,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun WinnerDropdownPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            //기본 상태
            var selectedWinner1 by remember { mutableStateOf<String?>(null) }
            val winnerItems1 = remember {
                listOf("신형철", "공승준")
            }

            SmashingWinnerDropdown(
                selectedItem = selectedWinner1,
                items = winnerItems1,
                onClick = { winner ->
                    selectedWinner1 = winner
                    println("$winner 선택됨")
                },
            )

            Spacer(modifier = Modifier.height(200.dp))

            // 긴 이름 포함
            var selectedWinner2 by remember { mutableStateOf<String?>(null) }
            val winnerItems2 = remember {
                listOf(
                    "열글자열글자열글자열",
                    "세글자"
                )
            }

            SmashingWinnerDropdown(
                selectedItem = selectedWinner2,
                items = winnerItems2,
                onClick = { winner ->
                    selectedWinner2 = winner
                    println("$winner 선택됨")
                },
                isDivide = true,
            )
        }
    }
}