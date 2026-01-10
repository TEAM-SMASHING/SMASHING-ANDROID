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

/**
 * @param label 드롭다운 메뉴의 레이블
 *
 * Normal
 * 일반 드롭다운 메뉴 항목
 * 선택 시 onItemClick 콜백에 label을 전달합니다.
 *
 * Additional
 * 추가 액션을 가진 드롭다운 메뉴 항목
 * 선택 시 별도의 onClick 콜백을 실행합니다.
 */
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

/**
 * 드롭다운 메뉴 컴포넌트
 * Popup을 사용하여 트리거 요소 하단에 메뉴를 표시하는 드롭다운 메뉴입니다.
 * Normal과 Additional 두 가지 타입의 항목을 지원하며, 각 항목은 클릭 시 다른 동작을 수행합니다.
 * @param items 드롭다운에 표시할 항목 리스트 (Normal 또는 Additional 타입)
 * @param isExpanded 드롭다운 메뉴가 열려있는지 여부
 * @param triggerWidth 트리거 요소의 너비 (메뉴 최소 너비로 사용)
 * @param triggerHeight 트리거 요소의 높이 (메뉴 위치 계산에 사용)
 * @param density 화면 밀도 정보 (Dp를 픽셀로 변환하는데 사용)
 * @param onItemClick Normal 타입 항목이 클릭되었을 때 호출되는 콜백 (선택된 label을 전달)
 * @param onDismiss 드롭다운 메뉴가 닫힐 때 호출되는 콜백
 * @param modifier 적용할 Modifier
 * @param itemAlignment 드롭다운 메뉴의 정렬 위치 (기본값: TopEnd)
 * @param offsetY 트리거 요소로부터의 수직 오프셋 (기본값: 10.dp)
 * @param isDivided 항목 사이에 구분선을 표시할지 여부 (기본값: true)
 */
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