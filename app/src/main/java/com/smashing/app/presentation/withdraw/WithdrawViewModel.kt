package com.smashing.app.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.presentation.withdraw.WithdrawContract.WithdrawSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val myRepository: MyRepository,
    private val authManager: AuthManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(WithdrawContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<WithdrawSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun updateWithdrawalAgreed(agreed: Boolean) = _uiState.update {
        it.copy(isWithdrawalAgreed = agreed)
    }

    private fun updateWithdrawUiState(state: WithdrawUiState) = _uiState.update {
        it.copy(withdrawUiState = state)
    }

    fun postWithdraw() {
        if (_uiState.value.withdrawUiState == WithdrawUiState.Loading) return
        viewModelScope.launch {
            updateWithdrawUiState(WithdrawUiState.Loading)
            myRepository.postWithdraw()
                .onSuccess {
                    updateWithdrawUiState(WithdrawUiState.Success)
                    authManager.onUserLoggedOut()
                    _sideEffect.emit(WithdrawSideEffect.NavigateToLogin)
                }
                .onFailure { error ->
                    updateWithdrawUiState(
                        WithdrawUiState.Failure(
                            error.message ?: "탈퇴 실패했습니다",
                        ),
                    )
                }
        }
    }
}
