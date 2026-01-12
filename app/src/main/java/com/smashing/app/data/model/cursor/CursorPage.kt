package com.smashing.app.data.model.cursor

data class CursorPage<T>(
    val items: List<T>,
    val cursor: Cursor,
)
