package com.smashing.app.presentation.tierinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.core.designsystem.style.toTierInfoStyle
import com.smashing.app.data.model.rank.TierSkill
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.findSportType
import com.smashing.app.presentation.tierinfo.navigation.SportTierInfo
import com.smashing.app.presentation.tierinfo.util.TierInfoProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TierInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tierInfoProvider: TierInfoProvider,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TierInfoContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        updateInitialInfo(savedStateHandle)
    }

    fun updateTierInfo(tierInfoStyle: TierInfoStyle) {
        updateTierInfoDetail(_uiState.value.sportType, tierInfoStyle)
        _uiState.update {
            it.copy(
                selectedTierInfoStyle = tierInfoStyle,
            )
        }
    }

    fun updateSportType(sportType: SportType) = _uiState.update {
        it.copy(
            sportType = sportType
        )
    }

    fun updateTierInfoDetail(sportType: SportType, selectedTierInfoStyle: TierInfoStyle) {
        val baseTierInfoDetail = tierInfoProvider.getTierInfoDetail(sportType, selectedTierInfoStyle)

        val tierInfoDetail = if (selectedTierInfoStyle == TierInfoStyle.CHALLENGER) {
            val challengerMessage = tierInfoProvider.getChallengerNoUpgradeMessage()
            baseTierInfoDetail.copy(
                skills = listOf(
                    TierSkill(
                        name = challengerMessage,
                        description = "",
                    )
                )
            )
        } else {
            baseTierInfoDetail
        }

        _uiState.update {
            it.copy(
                tierInfoDetail = tierInfoDetail,
            )
        }
    }

    private fun updateInitialInfo(savedStateHandle: SavedStateHandle) {
        val route = savedStateHandle.toRoute<SportTierInfo>()
        val tierInfo = route.tierName.toTierInfoStyle()
        val sportType = route.sportName.findSportType()

        updateSportType(sportType)
        updateTierInfo(tierInfo)
    }
}