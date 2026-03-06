package com.smashing.app.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.presentation.mypage.MyPageContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myRepository: MyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    fun fetchProfileInfo() {
        if (_uiState.value.profileLoadState == MyPageUiState.Loading) return
        viewModelScope.launch {
            _uiState.update { it.copy(profileLoadState = MyPageUiState.Loading) }
            myRepository.getMyProfileInfo()
                .onSuccess { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            profileLoadState = MyPageUiState.Success,
                            myProfileInfo = data,
                            selectedSportProfileId = data.myProfileInfo.profileId,
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            profileLoadState = MyPageUiState.Failure(
                                exception.message ?: "오류 발생",
                            )
                        )
                    }
                }
        }
    }
}
