package com.smashing.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import com.smashing.app.presentation.splash.SplashContract.SideEffect.NavigateToHome
import com.smashing.app.presentation.splash.SplashContract.SideEffect.NavigateToLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenDataSource: LocalTokenDataSource,
    private val userDataSource: LocalUserDataSource,
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SplashContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun tryAutoLogin() = viewModelScope.launch {
        val accessToken = tokenDataSource.getAccessToken()
        val refreshToken = tokenDataSource.getRefreshToken()
        val userId = userDataSource.getUserId()

        if (accessToken != null && refreshToken != null && userId != null) {
            Timber.tag("Splash").d("자동로그인 성공 $userId")
            _sideEffect.emit(NavigateToHome)
        } else {
            Timber.tag("Splash").d("자동로그인 실패 $userId")
            _sideEffect.emit(NavigateToLogin)
        }
    }
}
