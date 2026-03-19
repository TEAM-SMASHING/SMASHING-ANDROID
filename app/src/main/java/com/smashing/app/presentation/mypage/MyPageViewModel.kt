package com.smashing.app.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.presentation.mypage.MyPageContract.MyPageUiState
import com.smashing.app.presentation.mypage.MyPageContract.State
import com.smashing.app.presentation.mypage.model.MyPageProfileUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myRepository: MyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()
    private val _sideEffect = MutableSharedFlow<MyPageUiState.MyPageSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchProfileInfo() {
        if (_uiState.value.profileLoadState == MyPageUiState.Loading) return
        viewModelScope.launch {
            _uiState.update { it.copy(profileLoadState = MyPageUiState.Loading) }
            myRepository.getMyProfileInfo()
                .onSuccess { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            profileLoadState = MyPageUiState.Success,
                            myPageProfileInfo = MyPageProfileUiModel(
                                profileId = data.profileInfo.profileId,
                                nickname = data.nickname,
                                tierType = data.profileInfo.tierType
                            )
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

    fun postLogout() {
        if (_uiState.value.logoutLoadState == MyPageUiState.Loading) return
        viewModelScope.launch {
            _uiState.update { it.copy(logoutLoadState = MyPageUiState.Loading) }
            myRepository.postLogout()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            logoutLoadState = MyPageUiState.Success,
                        )
                    }
                    _sideEffect.emit(MyPageUiState.MyPageSideEffect.NavigateToLogin)
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            logoutLoadState = MyPageUiState.Failure(
                                error.message ?: "로그아웃 실패"
                            )
                        )
                    }
                }
        }
    }

}
