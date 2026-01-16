package com.smashing.app.data.type

enum class GenderType(
    val gender: String,
) {
    FEMALE(
        gender = "여성",
    ),
    MALE(
        gender = "남성",
    );

    companion object {
        fun findGenderType(gender: String): GenderType? = entries.find { it.gender == gender }
    }
}
