package com.smashing.app.core.designsystem.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme

sealed class DropdownItem(
    val label: String,
) {
    class Normal(
        label: String,
    ) : DropdownItem(label)
    class Additional(
        label: String,
        val onClick: () -> Unit,
    ) : DropdownItem(label)
}

@Composable
fun SmashingDropdownMenu(
    items: List<DropdownItem>,
    isExpanded: Boolean,
    triggerWidth: Dp,
    triggerHeight: Dp,
    density: Density,
    onItemClick: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    itemAlignment: Alignment = Alignment.TopEnd,
    offsetY: Dp = 10.dp,
    isDivided: Boolean = true,
) {
    if (!isExpanded || items.isEmpty()) {
        return
    }

    Popup(
        alignment = itemAlignment,
        offset = IntOffset(
            x = 0,
            y = with(density) { (triggerHeight + offsetY).toPx().toInt() }
        ),
        onDismissRequest = onDismiss,
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Column(
            modifier = modifier
                .background(
                    color = colors.bgSurface,
                    shape = RoundedCornerShape(8.dp),
                )
                .wrapContentSize()
                .widthIn(min = triggerWidth)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items.forEachIndexed { index, item ->
                SmashingDropdownMenuItem(
                    item = item,
                    onNormalClick = onItemClick,
                    onDismiss = onDismiss,
                )

                if (index < items.size - 1 && isDivided) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = colors.borderPrimary,
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}

@Composable
private fun SmashingDropdownMenuItem(
    item: DropdownItem,
    onNormalClick: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp,
            )
            .noRippleClickable(
                onClick = {
                    when(item){
                        is DropdownItem.Normal -> onNormalClick(item.label)
                        is DropdownItem.Additional -> item.onClick()
                    }
                    onDismiss()
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = item.label,
            style = typography.sm.medium14,
            color = colors.txtSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmashingDropdownMenuPreview() {
    SmashingAndroidTheme {
        val density = LocalDensity.current
        var isExpanded by remember { mutableStateOf(false) }
        var triggerWidth by remember { mutableStateOf(0.dp) }
        var triggerHeight by remember { mutableStateOf(0.dp) }

        val items = remember {
            listOf(
                DropdownItem.Normal("서울"),
                DropdownItem.Normal("부산"),
                DropdownItem.Normal("인천"),
                DropdownItem.Additional(
                    label = "지역 선택",
                    onClick = { println("지역 선택 화면으로 이동") },
                ),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd,
        ) {
            // 트리거 버튼 시뮬레이션
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        triggerWidth = with(density) { coordinates.size.width.toDp() }
                        triggerHeight = with(density) { coordinates.size.height.toDp() }
                    }
                    .background(
                        color = colors.bgSurface,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .noRippleClickable(
                        onClick = { isExpanded = !isExpanded },
                    )
                    .padding(16.dp),
            ) {
                Text(
                    text = "드롭다운 열기",
                    style = typography.sm.medium14,
                    color = colors.txtPrimary,
                )
            }

            SmashingDropdownMenu(
                items = items,
                isExpanded = isExpanded,
                triggerWidth = triggerWidth,
                triggerHeight = triggerHeight,
                density = density,
                onItemClick = { label ->
                    println("$label 선택됨")
                },
                onDismiss = { isExpanded = false },
                isDivided = true,
            )
        }
    }
}