package com.smashing.app.data.mapper

import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.remote.dto.addsports.AddSportsProfileRequest


fun AddSportsInfo.toRequest(): AddSportsProfileRequest {
    return AddSportsProfileRequest(
        sportCode = this.selectedSports?.code ?: "null",
        experienceRange = this.selectedSkill?.skillCode ?: "LT_3_MONTHS",
    )
}
