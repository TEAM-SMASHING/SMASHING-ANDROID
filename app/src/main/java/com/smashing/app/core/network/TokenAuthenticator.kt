package com.smashing.app.core.network

import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.remote.dto.auth.PostTokenReissueRequest
import com.smashing.app.data.repository.api.AuthRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataStore: LocalTokenDataSource,
    private val authManager: AuthManager,
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= MAX_RESPONSE_COUNT) return null

        return runBlocking {
            updateToken(response)
        }
    }

    private suspend fun updateToken(response: Response): Request = mutex.withLock {
        val accessToken = tokenDataStore.getAccessToken()
        val oldAccessToken =
            response.request.header("Authorization")?.replace("$BEARER_SUFFIX ", "")

        if (accessToken != oldAccessToken && accessToken != null) {
            return response.request.newBuilder()
                .header("Authorization", "$BEARER_SUFFIX $accessToken")
                .build()
        }

        val refreshToken = tokenDataStore.getRefreshToken()
        var newAccessToken: String? = null

        if (refreshToken == null) {
            handleReissueFailure()
        } else {
            authRepository.postTokenReissue(PostTokenReissueRequest(refreshToken))
                .onSuccess {
                    Timber.tag("Authenticator").e("토큰 재발급 성공")
                    tokenDataStore.setTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                    )
                    newAccessToken = it.accessToken
                }
                .onFailure { error ->
                    Timber.tag(AUTHORIZATION).e("토큰 재발급 실패 : ${error.message}")
                    handleReissueFailure()
                }
        }

        return response.request.newBuilder()
            .header(AUTHORIZATION, "$BEARER_SUFFIX $newAccessToken")
            .build()
    }

    private suspend fun handleReissueFailure() {
        tokenDataStore.clearTokens()
        authManager.emitAuthEvent()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var response = response.priorResponse
        while (response != null) {
            count++
            response = response.priorResponse
        }
        return count
    }

    companion object {
        private const val MAX_RESPONSE_COUNT = 2
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER_SUFFIX = "Bearer"
    }
}
