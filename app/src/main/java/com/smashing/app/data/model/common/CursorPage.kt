package com.smashing.app.data.model.common

data class CursorPage<T>(
    val items: List<T>,
    val nextCursor: String?,
    val hasNext: Boolean,
    val snapshotAt: String,
)
