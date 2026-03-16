package com.smashing.app.core.security

import android.util.Base64
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.smashing.app.BuildConfig
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class CryptoManager @Inject constructor() : CryptoInterface {

    private val keyStore: KeyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply { load(null) }

    override suspend fun encrypt(data: String): Result<String> = runCatching {
        val secretKey = getOrCreateSecretKey()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val iv = cipher.iv
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        val blob = iv + encrypted
        Base64.encodeToString(blob, Base64.NO_WRAP)
    }.onFailure { throwable ->
        if (BuildConfig.DEBUG) {
            Timber.e(throwable, "토큰 암호화에 실패했습니다.")
        } else {
            Timber.e(throwable, "토큰 암호화 중 오류가 발생했습니다.")
        }
    }

    override suspend fun decrypt(data: String): Result<String> = runCatching {
        val blob = Base64.decode(data, Base64.NO_WRAP)
        require(blob.size > GCM_IV_LENGTH) { "Invalid encrypted blob length" }

        val iv = blob.copyOfRange(0, GCM_IV_LENGTH)
        val cipherText = blob.copyOfRange(GCM_IV_LENGTH, blob.size)
        decryptInternal(
            encryptedData = cipherText,
            iv = iv,
        )
    }.onFailure { throwable ->
        if (BuildConfig.DEBUG) {
            Timber.e(throwable, "토큰 복호화에 실패했습니다. 저장된 데이터가 손상되었을 수 있습니다.")
        } else {
            Timber.e(throwable, "토큰 복호화 중 오류가 발생했습니다.")
        }
    }

    private fun decryptInternal(encryptedData: ByteArray, iv: ByteArray): String {
        val secretKey = getOrCreateSecretKey()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(GCM_TAG_LENGTH, iv))
        return String(cipher.doFinal(encryptedData), Charsets.UTF_8)
    }

    private fun getOrCreateSecretKey(): SecretKey {
        keyStore.getKey(KEY_ALIAS, null)?.let { return it as SecretKey }

        val keySpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setRandomizedEncryptionRequired(true)
            setUserAuthenticationRequired(false)
        }.build()

        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER)
            .apply { init(keySpec) }
            .generateKey()
    }

    companion object {
        private const val KEY_ALIAS = "UserAccessToken"
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_TAG_LENGTH = 128
        private const val GCM_IV_LENGTH = 12
    }
}
