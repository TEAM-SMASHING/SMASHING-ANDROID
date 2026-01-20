<<<<<<<< HEAD:app/src/main/java/com/smashing/app/data/model/profile/my/AddSportsInfo.kt
package com.smashing.app.data.model.profile.my
========
package com.smashing.app.data.model.addsports
>>>>>>>> develop:app/src/main/java/com/smashing/app/data/model/addsports/AddSportsInfo.kt

import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType

data class AddSportsInfo(
    val selectedSports: SportType? = null,
    val selectedSkill: SkillType? = null,
)
