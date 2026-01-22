package com.smashing.app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.repository.api.EventRepository
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.presentation.main.MainContract.SideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = _sideEffect.asSharedFlow()

    init {
        observeSseEvents()
    }

    private fun observeSseEvents() {
        viewModelScope.launch {
            eventRepository.events.collect { event ->
                when (event) {
                    is SseEvent.MatchingReceived -> {
                        _sideEffect.emit(
                            SideEffect.ShowToast("누군가가 매칭을 신청했어요! 받은 요청 탭에서 확인해주세요.")
                        )
                    }

                    is SseEvent.GameUpdated -> {
                        if (event.resultStatus == GameResultStatusType.WAITING_CONFIRMATION) {
                            _sideEffect.emit(
                                SideEffect.ShowToast("누군가가 매칭을 수락했어요! 매칭 확정 탭에서 확인해주세요.")
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}
