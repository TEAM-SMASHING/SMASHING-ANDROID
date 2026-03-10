package com.smashing.app.core.designsystem.state

import androidx.compose.runtime.Stable

/**
 * 탑바 UI 구성을 위한 상태 모델입니다.
 * 스타일별로 필요한 데이터와 콜백만 담은 sealed hierarchy입니다.
 *
 * - [Default] : 제목만 표시
 * - [Back] : 왼쪽 뒤로가기 아이콘 + 제목
 * - [Close] : 오른쪽 닫기 아이콘 + 제목
 * - [BackWithMenu] : 왼쪽 뒤로가기 + 제목 + 오른쪽 메뉴 아이콘
 */
@Stable
sealed interface TopBarState {
    val title: String

    @Stable
    data class Default(
        override val title: String,
    ) : TopBarState

    @Stable
    data class Back(
        override val title: String,
        val onBackClick: () -> Unit,
    ) : TopBarState

    @Stable
    data class Close(
        override val title: String,
        val onCloseClick: () -> Unit,
    ) : TopBarState

    @Stable
    data class BackWithMenu(
        override val title: String,
        val onBackClick: () -> Unit,
        val onMenuClick: () -> Unit,
    ) : TopBarState
}
