package com.smashing.app.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.auth.AuthModel
import com.smashing.app.data.remote.dto.PostSignUpRequest
import com.smashing.app.data.repository.api.AuthRepository
import com.smashing.app.presentation.signup.navigation.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val authId = savedStateHandle.toRoute<SignUp>().authId

    private val _uiState = MutableStateFlow(SignUpContract.State())
    val uiState = _uiState.asStateFlow()

    var currentStep by mutableStateOf(1)
        private set

    val progress: Float
        get() = when (currentStep) {
        1 -> 1f/6f
        2 -> 2f/6f
        3 -> 3f/6f
        4 -> 4f/6f
        5 -> 5f/6f
        else -> 1f
    }

    fun updateCurrentStep() {
        currentStep = currentStep + 1
    }


    /*fun updateNickNameText(text: String) = _uiState.update {
        it.copy(nicknameInput = text)
    }*/

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
