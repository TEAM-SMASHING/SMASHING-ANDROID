package com.smashing.app.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_close_lg
import com.smashing.app.core.common.type.ButtonType
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * 바텀 시트 공통 컴포넌트
 * 바텀 시트 구현시 사용하는 컴포넌트입니다.
 *
 * @param title 바텀 시트 내부 타이틀
 * @param items 바텀 시트 내부 리스트
 * @param contentToBtnPadding 바텀 시트 내부 리스트와 버튼 사이 간격
 * @param btnText 하단 버튼 텍스트
 * @param onDismissRequest 바텀 시트 사라짐
 * @param onBtnClick 하단 버튼 클릭 이벤트
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmashingBottomSheet(
    title: String,
    items: ImmutableList<String>,
    contentToBtnPadding: Dp,
    btnText: String,
    onDismissRequest: () -> Unit,
    onBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        ),
        sheetMaxWidth = Dp.Unspecified,
        sheetGesturesEnabled = false,
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
        ),
        containerColor = SmashingTheme.colors.bgSurface,
        scrimColor = SmashingTheme.colors.bgDimmed,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                width = 40.dp,
                color = SmashingTheme.colors.iconTertiary,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 47.dp,
                ),
        ) {

            var selectedItemIndex by remember { mutableStateOf<Int?>(null) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = title,
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = SmashingTheme.colors.txtPrimary,
                    style = SmashingTheme.typography.lg.semibold18,
                )

                Icon(
                    imageVector = ImageVector.vectorResource(ic_close_lg),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable(
                            onClick = onDismissRequest
                        )
                        .align(alignment = Alignment.CenterEnd),
                    tint = SmashingTheme.colors.iconPrimary,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            items.forEachIndexed { index, item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable(onClick = { selectedItemIndex = index })
                        .background(
                            color = (
                                    if (selectedItemIndex == index)
                                        SmashingTheme.colors.bgSurfacePressed
                                     else Color.Unspecified
                                    ),
                        )
                        .padding(
                            start = 16.dp,
                            top = 17.dp,
                            bottom = 17.dp
                        ),
                    color = SmashingTheme.colors.txtSecondary,
                    style = SmashingTheme.typography.sm.regular14,
                )

            }

            Spacer(modifier = Modifier.height(contentToBtnPadding))

            SmashingButton(
                buttonType = ButtonType.PRIMARY_WITH_DISABLED,
                text = btnText,
                onClick = onBtnClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp),
                isEnabled = selectedItemIndex != null,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SmashingBottomSheetPreview() {
    SmashingAndroidTheme {

        var isBottomSheetShow1 by remember { mutableStateOf(false) }
        var isBottomSheetShow2 by remember { mutableStateOf(false) }

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
                    text = "매칭결과"
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { isBottomSheetShow2 = true },
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "매칭관리"
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
                    contentToBtnPadding = 20.dp,
                    btnText = "완료",
                    onBtnClick = {
                        isBottomSheetShow1 = false
                    },
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
                    contentToBtnPadding = 4.dp,
                    btnText = "적용하기",
                    onBtnClick = {
                        isBottomSheetShow2 = false
                    },
                )
            }
        }
    }
}

