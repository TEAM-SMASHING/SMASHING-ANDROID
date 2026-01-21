package com.smashing.app.core.util

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

/**
 * 스크롤 상태를 추상화하는 sealed interface.
 */
sealed interface ScrollStateHolder {
    fun isAtTop(): Boolean

    /** Column + verticalScroll용 */
    data class Scroll(val state: ScrollState) : ScrollStateHolder {
        override fun isAtTop(): Boolean = state.value == 0
    }

    /** LazyVerticalGrid용 */
    data class LazyGrid(val state: LazyGridState) : ScrollStateHolder {
        override fun isAtTop(): Boolean =
            state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset == 0
    }
}

/**
 * 스크롤 방향에 따라 바텀바 표시/숨김을 제어하는 [NestedScrollConnection] 생성.
 *
 * - 아래로 스크롤: 바텀바 숨김
 * - 위로 스크롤: 바텀바 표시
 * - 최상단 도달: 바텀바 표시
 *
 * @param scrollStateHolder 스크롤 상태 홀더
 * @param onBottomBarVisibilityChange 바텀바 표시 여부 콜백 (true=표시, false=숨김)
 * @param scrollThreshold 상태 변경 트리거 임계값 (기본 30f)
 */
@Composable
fun bottomBarNestedScrollConnection(
    scrollStateHolder: ScrollStateHolder,
    onBottomBarVisibilityChange: (Boolean) -> Unit,
    scrollThreshold: Float = 30f,
): NestedScrollConnection {
    return remember(scrollStateHolder) {
        var accumulatedScroll = 0f

        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (scrollStateHolder.isAtTop()) {
                    onBottomBarVisibilityChange(true)
                    accumulatedScroll = 0f
                    return Offset.Zero
                }

                accumulatedScroll += available.y

                if (accumulatedScroll < -scrollThreshold) {
                    onBottomBarVisibilityChange(false)
                    accumulatedScroll = 0f
                } else if (accumulatedScroll > scrollThreshold) {
                    onBottomBarVisibilityChange(true)
                    accumulatedScroll = 0f
                }
                return Offset.Zero
            }
        }
    }
}