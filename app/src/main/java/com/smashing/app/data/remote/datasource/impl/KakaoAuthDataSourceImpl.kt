package com.smashing.app.data.remote.datasource.impl

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.remote.datasource.api.KakaoAuthDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoAuthDataSourceImpl() : KakaoAuthDataSource {
    override suspend fun loginKakao(context: Context): Result<String> =
        suspendRunCatching {
            val accessToken = getKakaoAccessToken(context)
            accessToken
        }

    private suspend fun getKakaoAccessToken(context: Context): String =
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
