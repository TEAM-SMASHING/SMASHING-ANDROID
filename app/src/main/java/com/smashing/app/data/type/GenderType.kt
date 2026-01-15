package com.smashing.app.data.type

import com.smashing.app.R.drawable.ic_man_20
import com.smashing.app.R.drawable.ic_woman_20


enum class GenderType(
    val gender: String,
    val iconRes: Int,
) {
    FEMALE(
        gender = "여성",
        iconRes = ic_woman_20,
    ),
    MALE(
        gender = "남성",
        iconRes = ic_man_20,
    );

    companion object {
        fun findGenderType(gender: String): GenderType? {
            return entries.find { it.gender == gender }
        }
    }
}
