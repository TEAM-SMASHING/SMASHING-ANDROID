package com.smashing.app.core.designsystem.style

enum class MatchingCardStyle(
    val isCancelAble: Boolean = false,
) {
    SEARCH,
    SEND(
        isCancelAble = true,
    ),
    RECEIVE,
    CONFIRM(
        isCancelAble = true,
    );
}
