package com.smashing.app.data.remote.dto.region.kakaoRegion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetKakaoAddressSearchResponse(
    @SerialName("meta") val meta: Meta,
    @SerialName("documents") val documents: List<Document>,
) {
    @Serializable
    data class Meta(
        @SerialName("total_count") val totalCount: Int,
        @SerialName("pageable_count") val pageableCount: Int,
        @SerialName("is_end") val isEnd: Boolean,
    )

    @Serializable
    data class Document(
        @SerialName("address_name") val addressName: String,
        @SerialName("y") val y: String? = null,
        @SerialName("x") val x: String? = null,
        @SerialName("address_type") val addressType: String? = null,
        @SerialName("address") val address: Address? = null,
        @SerialName("road_address") val roadAddress: RoadAddress? = null,
    ) {
        @Serializable
        data class Address(
            @SerialName("address_name") val addressName: String? = null,
            @SerialName("region_1depth_name") val region1depthName: String? = null,
            @SerialName("region_2depth_name") val region2depthName: String? = null,
            @SerialName("region_3depth_name") val region3depthName: String? = null,
            @SerialName("region_3depth_h_name") val region3depthHName: String? = null,
            @SerialName("h_code") val hCode: String? = null,
            @SerialName("b_code") val bCode: String? = null,
            @SerialName("mountain_yn") val mountainYn: String? = null,
            @SerialName("main_address_no") val mainAddressNo: String? = null,
            @SerialName("sub_address_no") val subAddressNo: String? = null,
            @SerialName("x") val x: String? = null,
            @SerialName("y") val y: String? = null,
        )

        @Serializable
        data class RoadAddress(
            @SerialName("address_name") val addressName: String? = null,
            @SerialName("region_1depth_name") val region1depthName: String? = null,
            @SerialName("region_2depth_name") val region2depthName: String? = null,
            @SerialName("region_3depth_name") val region3depthName: String? = null,
            @SerialName("road_name") val roadName: String? = null,
            @SerialName("underground_yn") val undergroundYn: String? = null,
            @SerialName("main_building_no") val mainBuildingNo: String? = null,
            @SerialName("sub_building_no") val subBuildingNo: String? = null,
            @SerialName("building_name") val buildingName: String? = null,
            @SerialName("zone_no") val zoneNo: String? = null,
            @SerialName("y") val y: String? = null,
            @SerialName("x") val x: String? = null,
        )
    }
}