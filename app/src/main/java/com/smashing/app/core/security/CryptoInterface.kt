package com.smashing.app.core.security

interface CryptoInterface {
    suspend fun encrypt(data: List<String>): EncryptedResult
    suspend fun decrypt(encryptedData: ByteArray, iv: ByteArray): String
}

data class EncryptedResult(
    val ciphertext: ByteArray,
    val iv: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptedResult

        if (!ciphertext.contentEquals(other.ciphertext)) return false
        if (!iv.contentEquals(other.iv)) return false

        return true
    }
    override fun hashCode(): Int {
        var result = ciphertext.contentHashCode()
        result = 31 * result + iv.contentHashCode()
        return result
    }
}