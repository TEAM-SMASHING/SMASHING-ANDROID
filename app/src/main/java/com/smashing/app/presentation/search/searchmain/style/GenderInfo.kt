package com.smashing.app.presentation.search.searchmain.style

enum class GenderInfo(
    val id: Long,
    val genderName: String?,
    val genderKName: String,
) {
    MALE(
        id = 1,
        genderName = "MALE",
        genderKName = "남성",
    ),

    FEMALE(
        id = 2,
        genderName = "FEMALE",
        genderKName = "여성",
    ),

    BOTH(
        id = 3,
        genderName = null,
        genderKName = "남여 모두",
    );

    companion object {
        private val ID_MAP: Map<String, GenderInfo> = GenderInfo.entries.associateBy { it.genderKName }
        fun findGenderInfo(genderKName: String?): GenderInfo? = ID_MAP[genderKName]
    }
}
