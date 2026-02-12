package com.smashing.app.data.mapper.my

import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest

fun AddSportsInfo.toRequest():
        AddSportProfileRequest {
    val sportCode = requireNotNull(this.selectedSports?.code) { "sportCode is required" }
    return AddSportProfileRequest(
        sportCode = sportCode,
        experienceRange = this.selectedSkill?.skillCode ?: "LT_3_MONTHS",
    )
}
