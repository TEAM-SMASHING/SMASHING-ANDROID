package com.smashing.app.core.common.type

import androidx.compose.ui.graphics.Color


//TODO 디자인 시스템 등록 후 수정 현재는 임시로 값 할당함
enum class ChipType(
    val backgroundColor: Color,
    val contentColor: Color,
    val borderColor: Color? = null,
) {
    ACTIVE(
        backgroundColor = Color(0xFFE2E6EA),
        contentColor = Color.Black,
        borderColor = Color(0xFFE2E6EA),
    ),
    INACTIVE(
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        borderColor = Color(0xFF252A36),
    ),
    DISABLED(
        backgroundColor = Color(0xFF252A36),
        contentColor = Color.White,
        borderColor = Color.Transparent,
    ),
    PRESSED(
        backgroundColor = Color(0xFFE2E6EA),
        contentColor = Color.Black,
        borderColor =Color.White
    )

}
