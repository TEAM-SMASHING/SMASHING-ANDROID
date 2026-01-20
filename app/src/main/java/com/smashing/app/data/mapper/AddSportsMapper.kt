package com.smashing.app.data.mapper

import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.remote.dto.addsports.AddSportProfileRequest


fun AddSportsInfo.toRequest(): AddSportProfileRequest {
    val sportCode = requireNotNull(this.selectedSports?.code) { "sportCode is required" }
    return AddSportProfileRequest(
        sportCode = sportCode,
        experienceRange = this.selectedSkill?.skillCode ?: "LT_3_MONTHS",
    )
}
