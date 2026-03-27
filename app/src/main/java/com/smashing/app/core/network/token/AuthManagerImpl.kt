package com.smashing.app.core.network.token

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthManagerImpl @Inject constructor() : AuthManager {
    private val _authEvent = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )
    override val authEvent = _authEvent.asSharedFlow()

    override fun emitAuthEvent() {
        _authEvent.tryEmit(Unit)
    }
}
