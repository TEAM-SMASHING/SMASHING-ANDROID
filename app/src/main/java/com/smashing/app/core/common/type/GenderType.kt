package com.smashing.app.core.common.type

enum class GenderType(
    val gender: String,
) {
    FEMALE("여성"),
    MALE("남성");

    companion object {
        fun findGenderType(gender: String): GenderType? {
            return GenderType.entries.find { it.gender == gender }
        }
    }
}
