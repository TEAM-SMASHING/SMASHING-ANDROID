package com.smashing.app.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun fetchKakaoLogin(
        context: Context,
        onKakaoLoginSuccess: () -> Unit,
    ) = viewModelScope.launch {
        authRepository.loginKakao(context = context)
            .onSuccess { token ->
                authRepository.fetchKakaoLogin(authorization = token)
                    .onSuccess {
                        onKakaoLoginSuccess()
                        Timber.tag("KakaoLogin").d("로그인 성공")
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
