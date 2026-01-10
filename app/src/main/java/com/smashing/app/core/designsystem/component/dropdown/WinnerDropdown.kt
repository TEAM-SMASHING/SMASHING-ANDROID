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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography

@Composable
fun WinnerDropdown(
    selectedItem: DropdownItem?,
    items: List<DropdownItem>,
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
        ) {
            Text(
                text = selectedItem?.label ?: placeholder,
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
            DropdownMenuPopup(
                items = items,
                isExpanded = isExpanded,
                triggerWidth = triggerWidth,
                triggerHeight = triggerHeight,
                density = density,
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
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // 1. 기본 상태 (선택 없음)
            var selectedItem1 by remember { mutableStateOf<DropdownItem?>(null) }

            val items1 = remember {
                listOf(
                    DropdownItem(
                        label = "김철수",
                        onClick = {
                            selectedItem1 = it
                            println("김철수 선택됨")
                        },
                    ),
                    DropdownItem(
                        label = "이영희이영희이영희",
                        onClick = {
                            selectedItem1 = it
                            println("이영희 선택됨")
                        },
                    ),
                    DropdownItem(
                        label = "박민수",
                        onClick = {
                            selectedItem1 = it
                            println("박민수 선택됨")
                        },
                    ),
                )
            }

            Text(
                text = "1. 기본 상태 (선택 없음)",
                style = typography.md.semibold16,
                color = colors.txtPrimary,
            )

            WinnerDropdown(
                selectedItem = selectedItem1,
                items = items1,
                placeholder = "승자 선택",
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2. 선택된 상태
            var selectedItem2 by remember { mutableStateOf<DropdownItem?>(null) }

            val items2 = remember {
                listOf(
                    DropdownItem(
                        label = "김철수",
                        onClick = {
                            selectedItem2 = it
                            println("김철수 선택됨")
                        },
                    ),
                    DropdownItem(
                        label = "이영희",
                        onClick = {
                            selectedItem2 = it
                            println("이영희 선택됨")
                        },
                    ),
                    DropdownItem(
                        label = "박민수",
                        onClick = {
                            selectedItem2 = it
                            println("박민수 선택됨")
                        },
                    ),
                )
            }

            // 초기값 설정
            if (selectedItem2 == null) {
                selectedItem2 = items2[0]
            }

            Text(
                text = "2. 선택된 상태 (김철수 선택됨)",
                style = typography.md.semibold16,
                color = colors.txtPrimary,
            )

            WinnerDropdown(
                selectedItem = selectedItem2,
                items = items2,
                placeholder = "승자 선택",
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. 여러 항목이 있는 경우 (구분선 포함)
            var selectedItem3 by remember { mutableStateOf<DropdownItem?>(null) }

            val items3 = remember {
                listOf(
                    DropdownItem(
                        label = "옵션 1",
                        onClick = {
                            selectedItem3 = it
                            println("옵션 1 선택")
                        },
                    ),
                    DropdownItem(
                        label = "옵션 2",
                        onClick = {
                            selectedItem3 = it
                            println("옵션 2 선택")
                        },
                    ),
                    DropdownItem(
                        label = "옵션 3",
                        onClick = {
                            selectedItem3 = it
                            println("옵션 3 선택")
                        },
                    ),
                    DropdownItem(
                        label = "옵션 4 (비활성화)",
                        onClick = {
                            selectedItem3 = it
                            println("옵션 4 선택")
                        },
                        isEnabled = false,
                    ),
                )
            }

            Text(
                text = "3. 여러 항목 (구분선 포함)",
                style = typography.md.semibold16,
                color = colors.txtPrimary,
            )

            WinnerDropdown(
                selectedItem = selectedItem3,
                items = items3,
                placeholder = "승자 선택",
                isDivide = true,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 4. 구분선 없음
            var selectedItem4 by remember { mutableStateOf<DropdownItem?>(null) }

            val items4 = remember {
                listOf(
                    DropdownItem(
                        label = "항목 A",
                        onClick = {
                            selectedItem4 = it
                            println("항목 A 선택")
                        },
                    ),
                    DropdownItem(
                        label = "항목 B",
                        onClick = {
                            selectedItem4 = it
                            println("항목 B 선택")
                        },
                    ),
                    DropdownItem(
                        label = "항목 C",
                        onClick = {
                            selectedItem4 = it
                            println("항목 C 선택")
                        },
                    ),
                )
            }

            Text(
                text = "4. 구분선 없음 (isDivide = false)",
                style = typography.md.semibold16,
                color = colors.txtPrimary,
            )

            WinnerDropdown(
                selectedItem = selectedItem4,
                items = items4,
                placeholder = "승자 선택",
                isDivide = false,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 5. 빈 리스트 (아이템 없음)
            Text(
                text = "5. 빈 리스트",
                style = typography.md.semibold16,
                color = colors.txtPrimary,
            )

            WinnerDropdown(
                selectedItem = null,
                items = emptyList(),
                placeholder = "승자 선택",
            )
        }
    }
}