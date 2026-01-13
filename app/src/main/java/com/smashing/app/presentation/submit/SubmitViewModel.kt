package com.smashing.app.presentation.submit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(SubmitContract.State())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedDropdownItem(dropdownItem: String) = _uiState.update {
        it.copy(
            selectedDropdownItem = dropdownItem,
        )
    }
}
