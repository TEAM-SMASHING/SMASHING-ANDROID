package com.smashing.app.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.core.common.type.SkillType
import com.smashing.app.core.common.type.SportType
import com.smashing.app.data.model.auth.SignUpModel
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

    private val kakaoId = savedStateHandle.toRoute<SignUp>().kakaoId

    private val _uiState = MutableStateFlow(SignUpContract.State())
    val uiState = _uiState.asStateFlow()

    fun updateCurrentStep() {
        _uiState.update {
            it.copy(currentStep = it.currentStep + 1)
        }
    }

    fun updateSelectedSport(sportType: SportType) {
        _uiState.update {
            it.copy(selectedSport = sportType)
        }
    }

    fun updateSelectedSkill(skillType: SkillType) {
        _uiState.update {
            it.copy(selectedSkill = skillType)
        }
    }

    fun postSignUp(
        onSignupSuccess: () -> Unit,
    ) = viewModelScope.launch {
        val request = PostSignUpRequest(
            kakaoId = kakaoId,
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
                    SignUpModel(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        userId = it.userId,
                    )
                }")
            }
            .onFailure { error ->
                Timber.tag("SignUp").e("회원가입 실패 $error")
            }
    }
}
