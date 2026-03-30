package com.smashing.app.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

/**
 * 하단 버튼이 있을 때 사용하는 설정.
 *
 * @param btnText 버튼에 표시할 텍스트
 * @param contentToBtnPadding 리스트와 버튼 사이 간격
 * @param onBtnClick 버튼 클릭 시 호출 (시트가 닫힌 뒤 호출됨)
 */
data class BottomSheetButtonConfig(
    val btnText: String,
    val contentToBtnPadding: Dp,
    val onBtnClick: () -> Unit,
)

/**
 * 바텀 시트 공통 컴포넌트입니다.
 *
 * @param items 바텀 시트 내부 리스트
 * @param selectedItem 내부 리스트 중 선택된 아이템
 * @param onItemClick 내부 리스트 아이템 클릭 이벤트
 * @param onDismissRequest 바텀 시트 사라짐
 * @param title 바텀 시트 내부 타이틀
 * @param optionalButton 바텀 시트 하단 버튼
 * @param itemTextColor 항목별 텍스트 색상 (null이면 기본 txtSecondary 사용)
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmashingBottomSheet(
    items: ImmutableList<String>,
    selectedItem: String,
    onItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    itemTextColor: @Composable (String) -> Color? = { null },
    optionalButton: BottomSheetButtonConfig? = null,
    bottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
) {
    val scope = rememberCoroutineScope()

    fun onCloseBottomSheet(onClosed: () -> Unit = {}) = scope.launch {
        if (!bottomSheetState.isVisible) return@launch
        bottomSheetState.hide()
        onDismissRequest()
        onClosed()
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = bottomSheetState,
        sheetMaxWidth = Dp.Unspecified,
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
        ),
        containerColor = colors.bgSurface,
        scrimColor = colors.bgDimmed,
        dragHandle = { CustomDragHandle() },
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 12.dp,
                ),
        ) {

            if (title != null) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp),
                    color = colors.txtPrimary,
                    textAlign = TextAlign.Center,
                    style = typography.lg.semibold18,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items.forEach { item ->

                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable(onClick = { onItemClick(item) })
                        .background(
                            color = (
                                    if (selectedItem == item)
                                        colors.bgSurfacePressed
                                    else Color.Unspecified
                                    ),
                        )
                        .padding(
                            horizontal = 17.dp,
                            vertical = 16.dp,
                        ),
                    color = itemTextColor(item) ?: colors.txtSecondary,
                    style = typography.sm.regular14,
                )

            }

            if (optionalButton != null) {
                Spacer(modifier = Modifier.height(optionalButton.contentToBtnPadding))
                SmashingButton(
                    buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                    text = optionalButton.btnText,
                    onClick = { onCloseBottomSheet(onClosed = optionalButton.onBtnClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp),
                    isEnabled = selectedItem.isNotEmpty(),
                )
                Spacer(modifier = Modifier.height(35.dp))
            }
        }
    }
}

@Composable
private fun CustomDragHandle(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(vertical = 8.dp)
            .size(width = 40.dp, height = 4.dp)
            .background(
                color = colors.borderSecondary,
                shape = RoundedCornerShape(28.0.dp),
            ),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SmashingBottomSheetPreview() {
    SmashingAndroidTheme {

        var isBottomSheetShow1 by remember { mutableStateOf(true) }
        var isBottomSheetShow2 by remember { mutableStateOf(false) }

        var selectedItem1 by remember { mutableStateOf("") }
        var selectedItem2 by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .padding(20.dp),
        ) {
            Button(
                onClick = { isBottomSheetShow1 = true },
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "매칭결과\n$selectedItem1"
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { isBottomSheetShow2 = true },
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "매칭관리\n$selectedItem2"
                )
            }

            if (isBottomSheetShow1) {
                SmashingBottomSheet(
                    onDismissRequest = {
                        isBottomSheetShow1 = false
                    },
                    title = "매칭결과",
                    items = persistentListOf(
                        "스코어가 잘못됐어요",
                        "승자가 잘못됐어요",
                        "스코어와 승자가 모두 잘못됐어요",
                        "아직 진행하지 않은 경기에요",
                    ),
                    selectedItem = selectedItem1,
                    onItemClick = { selectedItem1 = it },
                    optionalButton = BottomSheetButtonConfig(
                        btnText = "완료",
                        contentToBtnPadding = 20.dp,
                        onBtnClick = {}
                    )
                )
            }

            if (isBottomSheetShow2) {
                SmashingBottomSheet(
                    onDismissRequest = {
                        isBottomSheetShow2 = false
                    },
                    title = "매칭관리",
                    items = persistentListOf(
                        "아이언",
                        "브론즈",
                        "실버",
                        "골드",
                        "플래티넘",
                        "다이아",
                        "챌린저",
                    ),
                    selectedItem = selectedItem2,
                    onItemClick = { selectedItem2 = it },
                    optionalButton = BottomSheetButtonConfig(
                        btnText = "완료",
                        contentToBtnPadding = 20.dp,
                        onBtnClick = {}
                    )
                )
            }
        }
    }
}

