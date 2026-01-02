package com.smashing.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SmashingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKakaoSdk()
        initTimber()
        setDayMode()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }
}
