package com.smashing.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class UserProfileInfo(
    @SerialName("activeSport") val activeSport: ActiveSportProfile?,
    @SerialName("sports") val otherSports:List<SportSummary>,
)


@Serializable
data class ActiveSportProfile (
    val profileId:String,
    val sportCode:String,
    val tier:Int,
    val lp:Int,
    val minLp:Int,
    val maxLp:Int,
    val wins:Int,
    val losses:Int,
)

@Serializable
data class SportSummary(
    val profileId:String,
    val sportCode:String,
)
