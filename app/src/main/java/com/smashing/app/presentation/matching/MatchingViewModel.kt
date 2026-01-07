package com.smashing.app.presentation.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.common.state.UiState
import com.smashing.app.data.model.matching.ReceivedMatchingItem
import com.smashing.app.data.repository.api.MatchingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val matchingRepository: MatchingRepository,
) : ViewModel() {

    // TODO 임시 구성
    private val _uiState = MutableStateFlow(MatchingContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchReceivedMatchingList()
    }

    private var snapshotAt: String? = null

    fun fetchReceivedMatchingList() {
        viewModelScope.launch {
            matchingRepository.getReceivedMatchings(
                snapshotAt = null,
                cursor = null,
                size = 20,
            ).onSuccess { cursorPage ->
                snapshotAt = cursorPage.snapshotAt
                updateReceivedMatchingList(cursorPage.items.toImmutableList())
            }.onFailure {
                // TODO 에러 처리
            }
        }
    }

    private fun updateUiState(
        uiState: UiState<Unit>,
    ) = _uiState.update {
        it.copy(uiState = uiState)
    }

    private fun updateReceivedMatchingList(
        receivedMatchingList: ImmutableList<ReceivedMatchingItem>,
    ) = _uiState.update {
        it.copy(receivedMatchingList = receivedMatchingList)
    }
}
