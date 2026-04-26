package com.smashing.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.domain.usecase.auth.TokenReissueUseCase
import com.smashing.app.presentation.splash.SplashContract.SideEffect.NavigateToHome
import com.smashing.app.presentation.splash.SplashContract.SideEffect.NavigateToLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenReissueUseCase: TokenReissueUseCase,
    private val authManager: AuthManager,
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SplashContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun tryAutoLogin() {
        viewModelScope.launch {
            val delayTime = async {
                delay(SPLASH_DELAY)
            }

            val reissueToken = async { tokenReissueUseCase() }

            delayTime.await()
            reissueToken.await()
                .onSuccess {
                    Timber.tag(AUTHORIZATION).d("자동 로그인 성공")
                    authManager.onUserLoggedIn()
                    _sideEffect.emit(NavigateToHome)
                }
                .onFailure { error ->
                    Timber.tag(AUTHORIZATION).e("자동 로그인 실패 $error")
                    _sideEffect.emit(NavigateToLogin)
                }
        }
    }

    companion object {
        private const val SPLASH_DELAY = 2000L
        private const val AUTHORIZATION = "Authorization"
    }

}
