package com.smashing.app.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.network.sse.SseManager
import com.smashing.app.data.repository.api.AuthRepository
import com.smashing.app.presentation.login.LoginContract.SideEffect.NavigateToSignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sseManager: SseManager,
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<LoginContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun postKakaoLogin(
        context: Context,
    ) = viewModelScope.launch {
        authRepository.loginKakao(context = context)
            .onSuccess { token ->
                authRepository.postKakaoLogin(token)
                    .onSuccess {
                        if (it.isCompletedSignUp) {
                            sseManager.onUserLoggedIn()
                            _sideEffect.emit(LoginContract.SideEffect.NavigateToHome)
                        } else {
                            _sideEffect.emit(NavigateToSignUp(it.kakaoId))
                        }
                        Timber.tag("KakaoLogin").d("로그인 성공 $token")
                    }
                    .onFailure { error ->
                        Timber.tag("KakaoLogin").e("로그인 실패 : $error")
                    }
            }
            .onFailure { error ->
                Timber.tag("KakaoLogin").e("로그인 실패 : $error")
            }
    }
}
