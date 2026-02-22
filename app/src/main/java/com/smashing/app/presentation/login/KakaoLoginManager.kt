package com.smashing.app.presentation.login

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.smashing.app.core.util.suspendRunCatching
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoLoginManager {
    suspend fun loginKakao(
        context: Context,
    ): Result<String> =
        suspendRunCatching {
            getKakaoAccessToken(context)
        }

    private suspend fun getKakaoAccessToken(
        context: Context,
    ): String =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            suspendRunCatching {
                loginWithKakaoTalk(context)
            }.recoverCatching { error ->
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    throw error
                }
                loginWithKakaoAccount(context)
            }.getOrThrow()
        } else {
            loginWithKakaoAccount(context)
        }

    private suspend fun loginWithKakaoTalk(
        context: Context,
    ): String = suspendCancellableCoroutine { continuation ->
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                continuation.resumeWithException(error)
                return@loginWithKakaoTalk
            } else if (token != null) {
                continuation.resume(token.accessToken)
            }
        }
    }

    private suspend fun loginWithKakaoAccount(
        context: Context,
    ): String = suspendCancellableCoroutine { continuation ->
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {

                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    continuation.resumeWithException(error)
                    return@loginWithKakaoAccount
                }

                continuation.resumeWithException(error)

            } else if (token != null) {
                continuation.resume(token.accessToken)
            }
        }
    }
}
