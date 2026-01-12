package com.smashing.app.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.auth.SignUpModel
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

    fun fetchSignUp(
        onSignupSuccess: () -> Unit,
    ) = viewModelScope.launch {
        val request = PostSignUpRequest(
            authId = authId,
            nickname = "닉네임",
            gender = "여자",
            openChatUrl = "오픈채팅링크",
            sportCode = "스포츠코드",
            tier = "티어",
            region = "지역",
        )
        authRepository.postSignUp(request = request)
            .onSuccess {
                onSignupSuccess()
                Timber.tag("SignUp").d("회원가입 성공 ${SignUpModel(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken,
                    authId = it.authId,
                )}")
            }
            .onFailure { error ->
                Timber.tag("SignUp").e("회원가입 실패")
            }
    }
}
