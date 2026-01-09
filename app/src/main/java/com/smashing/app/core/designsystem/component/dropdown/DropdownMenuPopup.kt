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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun DropdownMenuPopup(
    items: List<DropdownItem>,
    isExpanded: Boolean,
    triggerWidth: Dp,
    triggerHeight: Dp,
    density: Density,
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
                    color = SmashingTheme.colors.bgSurface,
                    shape = RoundedCornerShape(8.dp),
                )
                .wrapContentSize()
                .widthIn(min = triggerWidth)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 10.dp,
                        )
                        .then(
                            if (item.isEnabled) {
                                Modifier.noRippleClickable {
                                    item.onClick(item)
                                    onDismiss()
                                }
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item.label,
                        style = SmashingTheme.typography.sm.medium14,
                        color = if (item.isEnabled) {
                            SmashingTheme.colors.txtSecondary
                        } else {
                            SmashingTheme.colors.txtDisabled
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                if (index < items.size - 1 && isDivided) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = SmashingTheme.colors.borderPrimary,
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DropdownMenuPopupPreview() {
    SmashingAndroidTheme {
        val density = LocalDensity.current
        var isExpanded by remember { mutableStateOf(false) }
        var triggerWidth by remember { mutableStateOf(0.dp) }
        var triggerHeight by remember { mutableStateOf(0.dp) }

        val items = remember {
            listOf(
                DropdownItem(
                    label = "열글자열글자열글자열",
                    onClick = { println("옵션 1 선택") },
                ),
                DropdownItem(
                    label = "옵션 2",
                    onClick = { println("옵션 2 선택") },
                ),
                DropdownItem(
                    label = "옵션 3",
                    onClick = { println("옵션 3 선택") },
                    isEnabled = false,
                ),
                DropdownItem(
                    label = "옵션 4",
                    onClick = { println("옵션 4 선택") },
                ),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .noRippleClickable(
                    onClick = { isExpanded = !isExpanded },
                ),
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
                        color = SmashingTheme.colors.bgSurface,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "드롭다운 열기",
                    style = SmashingTheme.typography.sm.medium14,
                    color = SmashingTheme.colors.txtPrimary
                )
            }

            DropdownMenuPopup(
                items = items,
                isExpanded = isExpanded,
                triggerWidth = triggerWidth,
                triggerHeight = triggerHeight,
                density = density,
                onDismiss = { isExpanded = false },
                isDivided = true,
            )
        }
    }
}
