package com.smashing.app.presentation.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.smashing.app.core.util.suspendRunCatching
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SocialLoginManager(
    val context: Context,
    val viewModel: LoginViewModel
) {
    suspend fun loginKakao(): Result<String> =
        suspendRunCatching {
            val accessToken = getKakaoAccessToken()
            accessToken
        }.onSuccess { token ->
            viewModel.postKakaoLogin(token)
        }.onFailure { error ->
            Timber.tag("KakaoLogin").e("로그인 실패 : $error")
        }

    suspend fun getKakaoAccessToken(): String =
        suspendCancellableCoroutine { continuation ->
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                    Timber.e("카카오계정으로 로그인 실패 ${error}")
                } else if (token != null) {
                    continuation.resume(token.accessToken)
                    Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        continuation.resumeWithException(error)
                        Timber.e("카카오톡으로 로그인 실패 ${error}")

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            continuation.resumeWithException(error)
                            return@loginWithKakaoTalk
                        }

                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        continuation.resume(token.accessToken)
                        Timber.i("카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
}
