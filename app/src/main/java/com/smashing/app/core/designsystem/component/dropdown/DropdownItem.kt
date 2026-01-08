package com.smashing.app.core.designsystem.component.dropdown

import androidx.compose.ui.text.TextStyle

/**
 * 드롭다운 항목 데이터 클래스
 * 각 항목마다 고유한 클릭 이벤트를 가질 수 있습니다.
 */
data class DropdownItem(
    val label: String,
    val labelStyle: TextStyle? = null,
    val onClick: (DropdownItem) -> Unit,
    val isEnabled: Boolean = true,
    val icon: Int? = null,
    val iconDescription: String? = null,
)