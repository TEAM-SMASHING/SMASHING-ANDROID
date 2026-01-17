package com.smashing.app.data.model.profile

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class AllUserProfiles(
    val allProfiles: ImmutableList<UserProfileItem>,
) {
    companion object {
        fun empty(): AllUserProfiles = AllUserProfiles(
            allProfiles = emptyList<UserProfileItem>().toImmutableList()
        )
    }
}