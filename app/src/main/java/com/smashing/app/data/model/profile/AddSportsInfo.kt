package com.smashing.app.data.model.profile

import com.smashing.app.core.common.type.SkillType
import com.smashing.app.core.common.type.SportType

data class AddSportsInfo(
    val selectedSports: List<SportType>,
    val selectedSkill: SkillType?,
)
