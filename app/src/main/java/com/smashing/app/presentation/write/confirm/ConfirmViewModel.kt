package com.smashing.app.presentation.write.confirm

import androidx.lifecycle.ViewModel
import com.smashing.app.core.common.type.ReviewRatingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConfirmViewMode @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfirmContract.State())
    val uiState = _uiState.asStateFlow()


    fun onToggle(type: ReviewRatingType) {
        _uiState.update { state ->
            val next = if (type in state.selectedRatingTypes) {
                state.selectedRatingTypes - type
            } else {
                state.selectedRatingTypes + type
            }
            state.copy(selectedRatingTypes = next.toImmutableSet())
        }
    }
}
