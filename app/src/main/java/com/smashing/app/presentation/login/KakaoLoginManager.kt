package com.smashing.app.presentation.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

class KakaoLoginManager (
    val context: Context,
){
    fun logInKakao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Timber.e("카카오계정으로 로그인 실패 ${error}")
            } else if (token != null) {
                Timber.i("카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Timber.e("카카오톡으로 로그인 실패 ${error}")

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun logOutKakao() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.e("로그아웃 실패. SDK에서 토큰 폐기됨 ${error}")
            } else {
                Timber.i("로그아웃 성공. SDK에서 토큰 폐기됨")
            }
        }
    }

    fun unlinkKakao() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.e("연결 해제 실패 ${error}")
            } else {
                Timber.i("연결 해제 성공. SDK에서 토큰 폐기 됨")
            }
        }
    }
}
