package com.smashing.app.core.designsystem.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.R.string.dropdown_winner_select
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * 승자 선택 드롭다운 컴포넌트
 * 승자를 선택하기 위한 드롭다운 컴포넌트입니다.
 * 선택된 항목이 없을 때는 placeholder를 표시하며, 선택 후에는 선택된 항목의 이름을 표시합니다.
 * @param selectedItem 현재 선택된 승자 이름 (null일 경우 placeholder 표시)
 * @param items 선택 가능한 승자 이름 리스트
 * @param onClick 항목이 클릭되었을 때 호출되는 콜백 (선택된 이름을 전달)
 * @param modifier 적용할 Modifier
 * @param placeholder 선택된 항목이 없을 때 표시할 안내 문구 (기본값: "승자 선택")
 * @param isDivide 항목 사이에 구분선을 표시할지 여부 (기본값: true)
 */

@Composable
fun SmashingWinnerDropdown(
    selectedItem: String?,
    items: ImmutableList<String>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
    placeholder: String = stringResource(dropdown_winner_select),
    isDivide: Boolean = true,
    enabled: Boolean = true,
) {
    var isExpanded by remember { mutableStateOf(false) }

    var triggerWidth by remember { mutableStateOf(0.dp) }
    var triggerHeight by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current
    val menuTextColor = if (selectedItem == null) colors.txtDisabled else colors.txtSecondary

    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .width(140.dp)
                .onGloballyPositioned { coordinates ->
                    triggerWidth = with(density) { coordinates.size.width.toDp() }
                    triggerHeight = with(density) { coordinates.size.height.toDp() }
                }
                .background(
                    color = colors.bgSurface,
                    shape = RoundedCornerShape(8.dp),
                )
                .noRippleClickable(
                    onClick = {
                        if (items.isNotEmpty() && enabled) {
                            isExpanded = !isExpanded
                        }
                    }
                )
                .padding(
                    start = if (selectedItem == null) 30.dp else 0.dp,
                    end = if (selectedItem == null) 22.dp else 0.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (selectedItem == null) Arrangement.Start else Arrangement.Center,
        ) {
            Text(
                text = selectedItem ?: placeholder,
                style = typography.sm.medium14,
                color = menuTextColor,
                modifier = Modifier.padding(vertical = 12.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (selectedItem == null) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_down),
                    contentDescription = null,
                    tint = colors.txtDisabled,
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
        }

        if (items.isNotEmpty()) {
            SmashingDropdownMenu(
                items = items.map {
                    DropdownItem.Normal(it)
                }.toImmutableList(),
                isExpanded = isExpanded,
                triggerWidth = triggerWidth,
                triggerHeight = triggerHeight,
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
                listOf("신형철", "공승준").toImmutableList()
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
                    "열글자열글자열글자열", "세글자"
                )
            }

            SmashingWinnerDropdown(
                selectedItem = selectedWinner2,
                items = winnerItems2.toImmutableList(),
                onClick = { winner ->
                    selectedWinner2 = winner
                    println("$winner 선택됨")
                },
                isDivide = true,
            )
        }
    }
}
