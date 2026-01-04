package com.smashing.app.presentation.login

import android.content.Context
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

class KakaoLoginManager (
    val context: Context,
){
    fun logInKakao(navigateToHome: () -> Unit) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(context,"카카오톡 로그인 실패", Toast.LENGTH_LONG).show()
                Timber.e("카카오계정으로 로그인 실패 ${error}")
            } else if (token != null) {
                Toast.makeText(context,"카카오톡 로그인 성공", Toast.LENGTH_LONG).show()
                Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
                navigateToHome()
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Toast.makeText(context,"카카오톡 로그인 실패", Toast.LENGTH_LONG).show()
                    Timber.e("카카오톡으로 로그인 실패 ${error}")

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Toast.makeText(context,"카카오톡 로그인 성공", Toast.LENGTH_LONG).show()
                    Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
                    navigateToHome()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun logOutKakao(navigateToLogin: () -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Toast.makeText(context,"카카오톡 로그아웃 실패", Toast.LENGTH_LONG).show()
                Timber.e("로그아웃 실패. SDK에서 토큰 폐기됨 ${error}")
            } else {
                Toast.makeText(context,"카카오톡 로그아웃 성공", Toast.LENGTH_LONG).show()
                Timber.i("로그아웃 성공. SDK에서 토큰 폐기됨")
                navigateToLogin()
            }
        }
    }

    fun unlinkKakao(navigateToLogin: () -> Unit) {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Toast.makeText(context,"카카오톡 연결 해제 실패", Toast.LENGTH_LONG).show()
                Timber.e("연결 해제 실패 ${error}")
            } else {
                Toast.makeText(context,"카카오톡 연결 해제 성공", Toast.LENGTH_LONG).show()
                Timber.i("연결 해제 성공. SDK에서 토큰 폐기 됨")
                navigateToLogin()
            }
        }
    }
}
