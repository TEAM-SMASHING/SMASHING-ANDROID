package com.smashing.app.core.network.token

import kotlinx.coroutines.flow.SharedFlow

interface AuthManager {
    val authEvent: SharedFlow<Unit>

    fun emitAuthEvent()
}
