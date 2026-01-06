package com.smashing.app.data.mapper

import com.smashing.app.data.model.DummyUser
import com.smashing.app.data.remote.dto.dummy.GetUserListResponse

fun GetUserListResponse.UserData.toDummyUser() = DummyUser(
    id = this.id ?: 0,
    email = this.email.orEmpty(),
    firstName = this.firstName.orEmpty(),
    lastName = this.lastName.orEmpty(),
    profileImage = this.profileImage.orEmpty(),
)
