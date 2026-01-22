package com.smashing.app.presentation.main

interface MainContract {
    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
    }
}
