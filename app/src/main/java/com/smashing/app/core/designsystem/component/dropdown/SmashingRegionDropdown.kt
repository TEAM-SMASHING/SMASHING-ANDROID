package com.smashing.app.core.designsystem.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme

/**
 * 지역 선택 드롭다운 컴포넌트
 * 지역을 선택하기 위한 드롭다운 컴포넌트입니다.
 * 위치 아이콘과 함께 현재 선택된 지역을 표시하며, 드롭다운 메뉴에는 선택 가능한 지역 목록과
 * "지역 선택" 추가 액션이 자동으로 포함됩니다.
 * @param selectedItem 현재 선택된 지역 이름
 * @param items 선택 가능한 지역 이름 리스트
 * @param onClick 지역 항목이 클릭되었을 때 호출되는 콜백 (선택된 지역 이름을 전달)
 * @param onRegionChange "지역 선택" 추가 항목이 클릭되었을 때 호출되는 콜백 (지역 선택 화면으로 이동하는 등의 동작)
 * @param modifier 적용할 Modifier
 * @param isDivide 항목 사이에 구분선을 표시할지 여부 (기본값: false)
 */
@Composable
fun RegionDropdown(
    selectedItem: String,
    items: List<String>,
    onClick: (String) -> Unit,
    onRegionChange: () -> Unit,
    modifier: Modifier = Modifier,
    isDivide: Boolean = false,
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
                .defaultMinSize(minWidth = 100.dp)
                .background(
                    color = Color.Transparent,
                )
                .noRippleClickable(
                    onClick = { isExpanded = !isExpanded }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_location),
                contentDescription = "location",
                tint = colors.iconPrimary,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = selectedItem,
                style = typography.lg.semibold18,
                color = colors.txtPrimary,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_down),
                contentDescription = "Dropdown Arrow",
                tint = colors.iconPrimary,
            )
        }
        if (items.isNotEmpty()) {
            SmashingDropdownMenu(
                items = items.map {
                    DropdownItem.Normal(it)
                } + DropdownItem.Additional(
                    label = "지역 선택",
                    onClick = onRegionChange,
                ),
                onItemClick = onClick,
                isExpanded = isExpanded,
                triggerWidth = triggerWidth,
                triggerHeight = triggerHeight,
                density = density,
                onDismiss = { isExpanded = false },
                itemAlignment = Alignment.TopStart,
                isDivided = isDivide,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegionDropdownPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .background(
                    color = colors.bgDimmed
                )
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // 1. 기본 상태 (서울 선택)
            var selectedRegion1 by remember { mutableStateOf("강남구") }
            val regionItems1 = remember {
                listOf("강남구", "강서구", "장신구")
            }

            RegionDropdown(
                selectedItem = selectedRegion1,
                items = regionItems1,
                onClick = { region ->
                    selectedRegion1 = region
                    println("$region 선택됨")
                },
                onRegionChange = {},
                isDivide = false,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 2. 다른 지역 선택 (부산)
            var selectedRegion2 by remember { mutableStateOf("부산") }
            val regionItems2 = remember {
                listOf("서울", "부산", "인천", "대구", "광주", "대전", "울산")
            }

            RegionDropdown(
                selectedItem = selectedRegion2,
                items = regionItems2,
                onClick = { region ->
                    selectedRegion2 = region
                    println("$region 선택됨")
                },
                onRegionChange = {
                    println("지역 선택 화면으로 이동")
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. 긴 지역명 (구분선 포함)
            var selectedRegion3 by remember { mutableStateOf("경기도 수원시") }
            val regionItems3 = remember {
                listOf(
                    "서울특별시",
                    "부산광역시",
                    "인천광역시",
                    "경기도 수원시",
                    "경기도 성남시",
                    "경기도 고양시",
                )
            }
            RegionDropdown(
                selectedItem = selectedRegion3,
                items = regionItems3,
                onClick = { region ->
                    selectedRegion3 = region
                    println("$region 선택됨")
                },
                onRegionChange = {
                    println("지역 선택 화면으로 이동")
                },
            )
        }
    }
}