package com.smashing.app.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.smashing.app.presentation.signup.navigation.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val authId = savedStateHandle.toRoute<SignUp>().authId

}
