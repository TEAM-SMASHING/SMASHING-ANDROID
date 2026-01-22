package com.smashing.app.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SSE

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Kakao

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth
