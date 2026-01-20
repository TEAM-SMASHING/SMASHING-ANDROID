package com.smashing.app.data.repository.api

import com.smashing.app.data.model.addsports.AddSportsInfo

interface AddSportsRepository {
        suspend fun addSportsProfile(info: AddSportsInfo): Result<Unit>
}
