package com.smashing.app.core.network.token

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharedFlow

interface AuthManager {
    val isUserLoggedIn: StateFlow<Boolean>
    val forceLogoutEvent: SharedFlow<Unit>

    fun onUserLoggedIn()
    fun onUserLoggedOut()
    fun onAuthFailure()
}
