package com.smashing.app.domain.usecase.auth

import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.remote.dto.auth.PostTokenReissueRequest
import com.smashing.app.data.repository.api.AuthRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 토큰 재발급 UseCase
 *
 * - 동시 재발급 요청을 `Mutex`로 차단
 * - 선행 요청이 이미 재발급한 토큰이 있으면 재사용
 * - 재발급 실패 시 토큰 정리 후 인증 실패 이벤트 전달
 */
@Singleton
class TokenReissueUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataSource: LocalTokenDataSource,
    private val authManager: AuthManager,
) {
    private val mutex = Mutex()

    /**
     * 토큰 재발급 실행 함수
     *
     * @return 재발급된 액세스 토큰 또는 실패 결과
     */
    suspend operator fun invoke(): Result<String> = mutex.withLock {
        val refreshToken = tokenDataSource.getRefreshToken()
            ?: return handleFailure(IllegalStateException("Refresh token is null"))

        return authRepository.postTokenReissue(PostTokenReissueRequest(refreshToken))
            .onSuccess {
                Timber.tag(TAG).d("토큰 재발급 성공")
            }
            .map { it.accessToken }
            .onFailure { error ->
                handleFailure(error)
            }
    }

    /**
     * 재발급 실패 처리 함수
     *
     * - 저장 토큰 정리
     * - 인증 실패 이벤트 전달
     */
    private suspend fun handleFailure(error: Throwable): Result<String> {
        Timber.tag(TAG).e("토큰 재발급 실패 : ${error.message}")
        tokenDataSource.clearTokens()
        authManager.onAuthFailure()
        return Result.failure(error)
    }

    private companion object {
        private const val TAG = "Authorization"
    }
}
