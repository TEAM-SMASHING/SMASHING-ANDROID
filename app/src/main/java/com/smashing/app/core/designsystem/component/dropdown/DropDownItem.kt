package com.smashing.app.core.designsystem.component.dropdown

import androidx.compose.runtime.Stable

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
@Stable
sealed interface DropdownItem {
    val label: String
    data class Normal(override val label: String) : DropdownItem
    class Additional(
        override val label: String,
        val onClick: () -> Unit,
    ) : DropdownItem
}
