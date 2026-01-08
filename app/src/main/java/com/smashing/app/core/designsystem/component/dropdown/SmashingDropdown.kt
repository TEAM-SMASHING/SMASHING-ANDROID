package com.smashing.app.core.designsystem.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmashingDropdown(
    selectedItem: DropdownItem?,
    items: List<DropdownItem>,
    modifier: Modifier = Modifier,
    placeholder: String = "승자 선택",
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .menuAnchor()
                .background(
                    color = SmashingTheme.colors.bgSurface,
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(vertical = 10.dp)
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = selectedItem?.label ?: placeholder,
                style = SmashingTheme.typography.sm.medium14,
                color = if (selectedItem == null) {
                    SmashingTheme.colors.txtDisabled
                } else {
                    SmashingTheme.colors.txtSecondary
                },
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_down),
                contentDescription = "Dropdown Arrow",
                tint = if (selectedItem == null) {
                    SmashingTheme.colors.txtDisabled
                } else {
                    SmashingTheme.colors.txtSecondary
                },
            )
        }
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .exposedDropdownSize()
                .background(
                    color = Color.Transparent
                )
                .offset(y = 2.dp),
            tonalElevation = 4.dp,
            containerColor = Color.Transparent,
            shadowElevation = 0.dp,
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = SmashingTheme.colors.bgSurface,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = item.label,
                                        style = SmashingTheme.typography.sm.medium14,
                                        color = if (item.isEnabled) {
                                            SmashingTheme.colors.txtSecondary
                                        } else {
                                            SmashingTheme.colors.txtDisabled
                                        }
                                    )
                                }
                            },
                            onClick = {
                                item.onClick(item)
                                isExpanded = false
                            },
                            enabled = item.isEnabled,
                        )

                        if (index < items.size - 1) {
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
    }
}

@Preview(showBackground = true)
@Composable
private fun SmashingDropdownPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // 1. 기본 상태 (선택 없음, 닫힌 상태)
            var selectedItem1 by remember { mutableStateOf<DropdownItem?>(null) }
            var expanded1 by remember { mutableStateOf(false) }

            val items1 = remember {
                listOf(
                    DropdownItem(
                        label = "name1",
                        onClick = {
                            selectedItem1 = it
                            println("name1 선택됨")
                        },
                    ),
                    DropdownItem(
                        label = "name2",
                        onClick = {
                            selectedItem1 = it
                            println("name2 선택됨")
                        },
                    ),
                )
            }

            Text(
                text = "1. 기본 상태 (선택 없음)",
                style = SmashingTheme.typography.md.semibold16,
                color = SmashingTheme.colors.txtPrimary,
            )

            SmashingDropdown(
                selectedItem = selectedItem1,
                items = items1,
                placeholder = "승자 선택",
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2. 선택된 상태
            var selectedItem2 by remember { mutableStateOf<DropdownItem?>(null) }
            var expanded2 by remember { mutableStateOf(false) }

            val items2 = remember {
                listOf(
                    DropdownItem(
                        label = "name1",
                        onClick = {
                            selectedItem2 = it
                            println("name1 선택됨")
                        },
                    ),
                    DropdownItem(
                        label = "name22222222222",
                        onClick = {
                            selectedItem2 = it
                            println("name2 선택됨")
                        },
                    ),
                )
            }

            // 초기값 설정
            if (selectedItem2 == null) {
                selectedItem2 = items2[0]
            }

            Text(
                text = "2. 선택된 상태 (name1 선택됨)",
                style = SmashingTheme.typography.md.semibold16,
                color = SmashingTheme.colors.txtPrimary,
            )

            SmashingDropdown(
                selectedItem = selectedItem2,
                items = items2,
                placeholder = "승자 선택",
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. 여러 항목이 있는 경우
            var selectedItem3 by remember { mutableStateOf<DropdownItem?>(null) }
            var expanded3 by remember { mutableStateOf(false) }

            val items3 = remember {
                listOf(
                    DropdownItem(
                        label = "옵션 1",
                        onClick = {
                            selectedItem3 = it
                            println("옵션 1 선택 - 특정 화면으로 이동")
                        },
                    ),
                    DropdownItem(
                        label = "옵션 2",
                        onClick = {
                            selectedItem3 = it
                            println("옵션 2 선택 - API 호출")
                        },
                    ),
                    DropdownItem(
                        label = "옵션 3",
                        onClick = {
                            selectedItem3 = it
                            println("옵션 3 선택 - 다이얼로그 표시")
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
                text = "3. 여러 항목 (비활성화 항목 포함)",
                style = SmashingTheme.typography.md.semibold16,
                color = SmashingTheme.colors.txtPrimary,
            )

            SmashingDropdown(
                selectedItem = selectedItem3,
                items = items3,
                placeholder = "옵션을 선택하세요",
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 4. 단일 항목만 있는 경우
            var selectedItem4 by remember { mutableStateOf<DropdownItem?>(null) }

            val items4 = remember {
                listOf(
                    DropdownItem(
                        label = "유일한 옵션",
                        onClick = {
                            selectedItem4 = it
                            println("유일한 옵션 선택")
                        },
                    ),
                )
            }

            Text(
                text = "4. 단일 항목",
                style = SmashingTheme.typography.md.semibold16,
                color = SmashingTheme.colors.txtPrimary,
            )

            SmashingDropdown(
                selectedItem = selectedItem4,
                items = items4,
                placeholder = "선택하세요",
            )
        }
    }
}

