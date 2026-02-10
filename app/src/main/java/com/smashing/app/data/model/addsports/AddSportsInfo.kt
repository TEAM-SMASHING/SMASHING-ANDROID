package com.smashing.app.data.model.addsports

import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType

data class AddSportsInfo(
    val selectedSports: SportType?,
    val selectedSkill: SkillType?,
)
