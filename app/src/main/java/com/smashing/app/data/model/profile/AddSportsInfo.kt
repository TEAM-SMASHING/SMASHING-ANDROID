package com.smashing.app.data.model.profile

import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType

data class AddSportsInfo(
    val selectedSports: SportType? = null,
    val selectedSkill: SkillType? = null,
)
