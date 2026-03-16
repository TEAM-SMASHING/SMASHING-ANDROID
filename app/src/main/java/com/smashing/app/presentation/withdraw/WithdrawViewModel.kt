package com.smashing.app.presentation.withdraw

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(WithdrawContract.State())
    val uiState = _uiState.asStateFlow()

    fun updateWithdrawalAgreed(agreed: Boolean) = _uiState.update { 
        it.copy(isWithdrawalAgreed = agreed)
    }
}
