package com.smashing.app.domain.usecase

import com.smashing.app.data.model.Region
import com.smashing.app.data.repository.api.RegionRepository
import javax.inject.Inject

class FilterRegionUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
) {
    suspend operator fun invoke(
        query: String,
    ): Result<List<Region>> {
        return regionRepository.searchAddress(query).map { regions ->
            regions.filter { it.cityName == "서울" && it.districtName.isNotEmpty() }
        }
    }
}