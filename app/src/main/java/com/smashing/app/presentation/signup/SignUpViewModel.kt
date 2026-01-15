package com.smashing.app.presentation.signup

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.SkillType
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.util.TextInputValidator
import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest
import com.smashing.app.data.repository.api.AuthRepository
import com.smashing.app.presentation.signup.SignUpContract.SideEffect.NavigateToHome
import com.smashing.app.presentation.signup.navigation.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _sideEffect = MutableSharedFlow<SignUpContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _nickNameState = TextFieldState("")
    val nickNameState: TextFieldState get() = _nickNameState

    var isNickNameAvailable: Boolean = false

    private val _openChatLinkState = TextFieldState("")
    val openChatLinkState: TextFieldState get() = _openChatLinkState

    val isBtnEnabled: Boolean
        get() = when(_uiState.value.currentStep) {
        1 -> isNickNameAvailable
        2 -> _uiState.value.selectedGender != null
        3 -> true
        4 -> _uiState.value.selectedSport != null
        5 -> _uiState.value.selectedSkill != null
        6 -> true
            else -> true
    }

    init {
        updateNickNameErrorText()
    }


    fun updateCurrentStep() {
        _uiState.update {
            it.copy(currentStep = it.currentStep + 1)
        }
    }

    fun updateNickNameErrorText() = viewModelScope.launch {
        snapshotFlow { nickNameState.text }
            .collect { nickNameText ->
                val text = nickNameText.toString()
                val isNickNameValid = TextInputValidator.isTextInputValid(text)
                if (text.isNotEmpty() && !isNickNameValid) {
                    _uiState.update {
                        it.copy(
                            nickNameErrorText = "특수문자는 사용할 수 없습니다."
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            nickNameErrorText = null,
                        )
                    }
                }
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
        //Todo: 오픈채팅 유효성 검증 api
    }

    fun getNickNameAvailable() = viewModelScope.launch {
        authRepository.getNicknameAvailable(nickNameState.text.toString())
            .onSuccess {
                _uiState.update {
                    it.copy(nickNameConfirmText = "사용 가능한 닉네임입니다.")
                }
                isNickNameAvailable = true
            }
            .onFailure { error ->
                _uiState.update {
                    it.copy(nickNameErrorText = "$error")}
            }
    }


    fun postSignUp() = viewModelScope.launch {
        val selectedGender = _uiState.value.selectedGender
        val selectedSport = _uiState.value.selectedSport
        val selectedSkill = _uiState.value.selectedSkill
        if(selectedGender != null && selectedSport != null && selectedSkill != null){
            val request = PostSignUpRequest(
                kakaoId = kakaoId,
                nickname = nickNameState.text.toString(),
                gender = selectedGender.name,
                openChatUrl = "https://open.kakao.com/o/xxxx",
                sportCode = selectedSport.code,
                tier = selectedSkill.skillCode,
                region = "양천구",
            )
            authRepository.postSignUp(request = request)
                .onSuccess {
                    _sideEffect.emit(NavigateToHome)
                    Timber.tag("SignUp").d(
                        "회원가입 성공 ${
                            SignUpModel(
                                accessToken = it.accessToken,
                                refreshToken = it.refreshToken,
                                userId = it.userId,
                            )
                        }"
                    )
                }
                .onFailure { error ->
                    Timber.tag("SignUp").e("회원가입 실패 $error")
                }
        }
    }
}
