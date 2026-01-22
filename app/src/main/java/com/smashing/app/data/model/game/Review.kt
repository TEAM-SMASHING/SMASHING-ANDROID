package com.smashing.app.data.model.game

data class Review(
    val rating: String,
    val content: String? = null,
    val tags: List<String>? = null,
)
