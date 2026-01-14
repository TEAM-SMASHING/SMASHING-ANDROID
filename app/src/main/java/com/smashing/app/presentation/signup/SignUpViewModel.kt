package com.smashing.app.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.auth.AuthModel
import com.smashing.app.data.remote.dto.PostSignUpRequest
import com.smashing.app.data.repository.api.AuthRepository
import com.smashing.app.presentation.signup.navigation.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val authId = savedStateHandle.toRoute<SignUp>().authId

    fun postSignUp(
        onSignupSuccess: () -> Unit,
    ) = viewModelScope.launch {
        val request = PostSignUpRequest(
            authId = authId,
            nickname = "이지민",
            gender = "FEMALE",
            openChatUrl = "https://open.kakao.com/o/xxxx",
            sportCode = "TT",
            tier = "IRON",
            region = "양천구",
        )
        authRepository.postSignUp(request = request)
            .onSuccess {
                onSignupSuccess()
                Timber.tag("SignUp").d("회원가입 성공 ${
                    AuthModel(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        authId = it.authId,
                    )
                }")
            }
            .onFailure { error ->
                Timber.tag("SignUp").e("회원가입 실패 $error")
            }
    }
}
