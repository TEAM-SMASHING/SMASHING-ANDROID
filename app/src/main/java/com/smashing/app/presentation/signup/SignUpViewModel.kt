package com.smashing.app.presentation.signup

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.core.util.TextInputValidator
import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidRequest
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest
import com.smashing.app.data.repository.api.AuthRepository
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType
import com.smashing.app.domain.model.Region
import com.smashing.app.presentation.signup.SignUpContract.SideEffect.NavigateToHome
import com.smashing.app.presentation.signup.SignUpContract.SignUpUiState
import com.smashing.app.presentation.signup.navigation.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
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

    val nickNameState = TextFieldState()

    val openChatState = TextFieldState()

    val isBtnEnabled: Boolean
        get() = when (_uiState.value.currentStep) {
            1 -> _uiState.value.isNickNameAvailable
            2 -> _uiState.value.selectedGender != null
            3 -> _uiState.value.isOpenChatValid
            4 -> _uiState.value.selectedSport != null
            5 -> _uiState.value.selectedSkill != null
            6 -> _uiState.value.isRegionSelected
            else -> true
        }

    init {
        updateNickNameErrorText()
        updateOpenChatErrorText()
    }


    fun updateCurrentStep() {
        _uiState.update {
            it.copy(currentStep = it.currentStep + 1 )
        }
    }

    fun deleteCurrentStep() {
        _uiState.update {
            it.copy(currentStep = maxOf(1, it.currentStep - 1))
        }
    }

    @OptIn(FlowPreview::class)
    fun updateNickNameErrorText() = viewModelScope.launch {
        snapshotFlow { nickNameState.text }
            .debounce(NETWORK_DEBOUNCE)
            .collectLatest { nickNameText ->
                val text = nickNameText.toString()
                val isNickNameValid = TextInputValidator.isTextInputValid(text)

                if (text.isEmpty()) {
                    _uiState.update { it.copy(nickNameErrorText = null, nickNameConfirmText = null, isNickNameAvailable = false) }
                } else if (text.isBlank() || !isNickNameValid) {
                    _uiState.update { it.copy(nickNameErrorText = INVALID_NICKNAME_FORMAT, nickNameConfirmText = null, isNickNameAvailable = false) }
                } else {
                    _uiState.update { it.copy(nickNameErrorText = null, nickNameConfirmText = null) }
                    getNickNameAvailable()
                }
            }
    }

    @OptIn(FlowPreview::class)
    fun updateOpenChatErrorText() = viewModelScope.launch {
        snapshotFlow { openChatState.text }
            .debounce(NETWORK_DEBOUNCE)
            .collectLatest { openChatText ->
                val text = openChatText.toString()

                if(text.isEmpty()) {
                    _uiState.update { it.copy(openChatErrorText = null, isOpenChatValid = false) }
                } else {
                    postOpenchatValid()
                }
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

    fun updateSelectedRegion(region: Region) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRegion = region,
                isRegionSelected = true,
                regionLoadState = SignUpUiState.Success,
            )
        }

    }

    fun postOpenchatValid()  = viewModelScope.launch {
        val request = PostOpenchatValidRequest(
            openchatUrl = openChatState.text.toString()
        )
        authRepository.postOpenchatValid(request)
            .onSuccess {
                if(it.valid) {
                    _uiState.update { it.copy(openChatErrorText = null, isOpenChatValid = true) }
                } else {
                    _uiState.update { it.copy(openChatErrorText = INVALID_OPEN_CHAT_FORMAT, isOpenChatValid = false) }
                }
            }
            .onFailure { error ->
                Timber.tag("SignUp").e("채팅 링크 유효성 확인 실패 $error")
            }
    }

    fun getNickNameAvailable() = viewModelScope.launch {
        authRepository.getNicknameAvailable(nickNameState.text.toString())
            .onSuccess {
                if (it.available) {
                    _uiState.update {
                        it.copy(nickNameConfirmText = VALID_NICKNAME_FORMAT, nickNameErrorText = null, isNickNameAvailable = true)
                    }
                } else {
                    _uiState.update {
                        it.copy(nickNameErrorText = DUPLICATE_NICKNAME, nickNameConfirmText = null, isNickNameAvailable = false)
                    }
                }
            }
            .onFailure { error ->
                Timber.tag("SignUp").e("닉네임 중복확인 실패 $error")
            }
    }


    fun postSignUp() = viewModelScope.launch {
        val selectedGender = _uiState.value.selectedGender
        val selectedSport = _uiState.value.selectedSport
        val selectedSkill = _uiState.value.selectedSkill
        val selectedRegion = _uiState.value.selectedRegion
        if (selectedGender != null && selectedSport != null
            && selectedSkill != null && selectedRegion != null) {
            val request = PostSignUpRequest(
                kakaoId = kakaoId,
                nickname = nickNameState.text.toString(),
                gender = selectedGender.name,
                openChatUrl = openChatState.text.toString(),
                sportCode = selectedSport.code,
                experienceRange = selectedSkill.skillCode,
                region = selectedRegion.districtName,
            )
            authRepository.postSignUp(request = request)
                .onSuccess {
                    _sideEffect.emit(NavigateToHome)
                }
                .onFailure { error ->
                    Timber.tag("SignUp").e("회원가입 실패 $error")
                }
        }
    }

    companion object SignUpConstants {
        private const val NETWORK_DEBOUNCE = 500L
        private const val INVALID_NICKNAME_FORMAT = "특수문자는 사용할 수 없습니다."
        private const val VALID_NICKNAME_FORMAT = "사용 가능한 닉네임입니다."
        private const val DUPLICATE_NICKNAME = "이미 존재하는 닉네임입니다."
        private const val INVALID_OPEN_CHAT_FORMAT = "유효하지 않은 링크입니다."
    }
}
