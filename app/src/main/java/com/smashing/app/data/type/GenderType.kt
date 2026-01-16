package com.smashing.app.data.type

enum class GenderType(
    val displayName: String,
) {
    FEMALE(
        displayName = "여성",
    ),
    MALE(
        displayName = "남성",
    );

    companion object {
        fun findByName(name: String): GenderType? = entries.find { it.name == name }
    }
}
