package com.smashing.app.core.network

import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenDataSource: LocalTokenDataSource,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val accessToken = runBlocking {
            tokenDataSource.getAccessToken()
        }

        val newRequest = request.newBuilder()
            .apply {
                if (accessToken.isNullOrBlank()) {
                    addHeader(AUTHORIZATION, "$BEARER_TOKEN $accessToken")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER_TOKEN = "Bearer"
    }

}
