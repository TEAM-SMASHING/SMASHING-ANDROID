package com.smashing.app.data.remote.dto.region.kakaoRegion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetKakaoAddressSearchResponse(
    @SerialName("documents") val documents: List<Document>,
) {
    @Serializable
    data class Document(
        @SerialName("address_name") val addressName: String,
        @SerialName("address") val address: Address? = null,
        @SerialName("road_address") val roadAddress: RoadAddress? = null,
    ) {
        @Serializable
        data class Address(
            @SerialName("address_name") val addressName: String? = null,
            @SerialName("region_1depth_name") val region1depthName: String? = null,
            @SerialName("region_2depth_name") val region2depthName: String? = null,
        )

        @Serializable
        data class RoadAddress(
            @SerialName("address_name") val addressName: String? = null,
            @SerialName("region_1depth_name") val region1depthName: String? = null,
            @SerialName("region_2depth_name") val region2depthName: String? = null,
        )
    }
}