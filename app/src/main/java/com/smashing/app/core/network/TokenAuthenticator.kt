package com.smashing.app.core.network

import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.domain.usecase.auth.TokenReissueUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenDataStore: LocalTokenDataSource,
    private val tokenReissueUseCase: TokenReissueUseCase,
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= MAX_RESPONSE_COUNT) return null

        return runBlocking {
            updateToken(response)
        }
    }

    private suspend fun updateToken(response: Response): Request? = mutex.withLock {
        val accessToken = tokenDataStore.getAccessToken()
        val oldAccessToken =
            response.request.header(AUTHORIZATION)?.replace("$BEARER_SUFFIX ", "")

        if (accessToken != oldAccessToken && accessToken != null) {
            return response.request.newBuilder()
                .header(AUTHORIZATION, "$BEARER_SUFFIX $accessToken")
                .build()
        }

        val newAccessToken = tokenReissueUseCase().getOrElse { return null }

        return response.request.newBuilder()
            .header(AUTHORIZATION, "$BEARER_SUFFIX $newAccessToken")
            .build()
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
