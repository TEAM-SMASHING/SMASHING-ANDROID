package com.smashing.app.data.model.cursor

data class Cursor(
    val snapshotAt: String? = null,
    val nextCursor: String? = null,
    val hasNext: Boolean = false,
)
