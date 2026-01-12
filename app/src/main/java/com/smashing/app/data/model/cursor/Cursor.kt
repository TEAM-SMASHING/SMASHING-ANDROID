package com.smashing.app.data.model.cursor

data class Cursor(
    val nextCursor: String?,
    val hasNext: Boolean,
    val snapshotAt: String,
)
