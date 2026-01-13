package com.smashing.app.presentation.signup.type


enum class SkillType(
    val skillText: String,
) {
    THREE(
        skillText = "3개월 미만",
    ),
    THREETOSIX(
        skillText = "3개월 이상 ~ 6개월 미만",
    ),
    SIXTOYEAR(
        skillText = "6개월 이상 ~ 1년 미만",
    ),
    YEARTOYEARSIX(
        skillText = "1년 이상 ~ 1년 6개월 미만",
    ),
    YEARSIX(
        skillText = "1년 6개월 이상",
    );
}
