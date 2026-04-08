package com.smashing.app.core.network.token

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class AuthManagerImpl @Inject constructor() : AuthManager {
    /**
     * 인증 실패 이벤트의 중복 처리를 막기 위한 가드.
     *
     * 여러 요청에서 동시에 토큰 재발급 실패가 발생할 수 있으므로,
     * 로그아웃 전환(forceLogoutEvent emit)은 최초 1회만 허용한다.
     * 사용자가 다시 로그인하면 false로 리셋한다.
     */
    private val authFailureHandled = AtomicBoolean(false)

    private val _isUserLoggedIn = MutableStateFlow(false)
    override val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    private val _forceLogoutEvent = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )
    override val forceLogoutEvent = _forceLogoutEvent.asSharedFlow()

    override fun onUserLoggedIn() {
        authFailureHandled.set(false)
        _isUserLoggedIn.value = true
    }

    override fun onUserLoggedOut() {
        _isUserLoggedIn.value = false
    }

    override fun onAuthFailure() {
        if (!authFailureHandled.compareAndSet(false, true)) {
            return
        }

        _isUserLoggedIn.value = false
        _forceLogoutEvent.tryEmit(Unit)
    }
}
