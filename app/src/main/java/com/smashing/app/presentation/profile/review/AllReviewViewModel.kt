package com.smashing.app.presentation.profile.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.presentation.profile.myprofile.MyProfileContract
import com.smashing.app.presentation.profile.myprofile.MyProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllReviewViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyProfileContract.State())
    val uiState: StateFlow<MyProfileContract.State> = _uiState.asStateFlow()

    init {
        fetchProfileData()
    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = MyProfileUiState.Loading) }

            try {
                // TODO: 실제 API 호출 (delay로 시뮬레이션)
                delay(1000)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loadState = MyProfileUiState.Failure(e.message ?: "Unknown Error"))
                }
            }
        }
    }
}
