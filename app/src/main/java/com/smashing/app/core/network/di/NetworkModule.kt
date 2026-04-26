package com.smashing.app.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.smashing.app.BuildConfig
import com.smashing.app.BuildConfig.BASE_URL
import com.smashing.app.BuildConfig.KAKAO_BASE_URL
import com.smashing.app.core.network.AuthInterceptor
import com.smashing.app.core.network.TokenAuthenticator
import com.smashing.app.core.network.isJsonArray
import com.smashing.app.core.network.isJsonObject
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.sse.EventSource
import okhttp3.sse.EventSources
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONTENT_TYPE = "application/json"
    private const val LOGGING_TAG = "okhttp"

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        prettyPrint = BuildConfig.DEBUG
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(CONTENT_TYPE.toMediaType())

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor { message ->
        when {
            message.isJsonObject() ->
                Timber.tag(LOGGING_TAG).d(JSONObject(message).toString(4))

            message.isJsonArray() ->
                Timber.tag(LOGGING_TAG).d(JSONArray(message).toString(4))

            else -> {
                Timber.tag(LOGGING_TAG).d("CONNECTION INFO -> $message")
            }
        }
    }.apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthInterceptor(
        tokenDataSource: LocalTokenDataSource,
    ): AuthInterceptor = AuthInterceptor(tokenDataSource)

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(
        loggingInterceptor: Interceptor,
        @Auth headerInterceptor: AuthInterceptor,
        authenticator: TokenAuthenticator,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .authenticator(authenticator)
        .build()

    @Provides
    @Singleton
    @NoAuth
    fun provideNoAuthOkHttpClient(
        loggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @Auth client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    @NoAuth
    fun provideNoAuthRetrofit(
        @NoAuth client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    @Kakao
    fun provideKakaoRetrofit(
        @NoAuth client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(KAKAO_BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    @SSE
    fun provideSSEOkhttpClient(
        @Auth headerInterceptor: AuthInterceptor,
        authenticator: TokenAuthenticator,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .authenticator(authenticator)
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideEventSourceFactory(
        @SSE client: OkHttpClient,
    ): EventSource.Factory = EventSources.createFactory(client)
}
