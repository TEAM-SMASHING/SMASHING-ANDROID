package com.smashing.app.domain.usecase

import com.smashing.app.data.repository.api.RegionRepository
import com.smashing.app.domain.mapper.toRegion
import com.smashing.app.domain.model.Region
import javax.inject.Inject

class GetSeoulFilterRegionUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
) {
    suspend operator fun invoke(
        query: String,
    ): Result<List<Region>> {
        return regionRepository.searchAddress(query).map { kakaoRegions ->
            kakaoRegions
                .map { it.toRegion() }
                .filter { !it.isDistrictBlank }
                .filter { it.isSeoul }
        }
    }
}