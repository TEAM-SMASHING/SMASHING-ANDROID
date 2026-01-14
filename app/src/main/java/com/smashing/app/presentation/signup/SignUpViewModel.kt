package com.smashing.app.presentation.signup

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.SkillType
import com.smashing.app.core.common.type.SportType
import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.remote.dto.PostSignUpRequest
import com.smashing.app.data.repository.api.AuthRepository
import com.smashing.app.presentation.signup.navigation.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _openChatLinkState = TextFieldState("")
    val openChatLinkState: TextFieldState get() = _openChatLinkState

    val isLinkValid by derivedStateOf {
        openChatLinkState
    }


    fun updateCurrentStep() {
        _uiState.update {
            it.copy(currentStep = it.currentStep + 1)
        }
    }

    suspend fun updateOpenChatLink() {
        snapshotFlow { openChatLinkState }
            .collectLatest { linkText ->
                //Todo 링크 유효성 판단 api (성공시 updateCurrentStep, 실패시 errorText 반환 및 이동 X
                postValidateChatLink()
            }
    }

    fun updateSelectedGender(genderType: GenderType) {
        _uiState.update {
            it.copy(selectedGender = genderType)
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

    fun postValidateChatLink(
    ) {
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
