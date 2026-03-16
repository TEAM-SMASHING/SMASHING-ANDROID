package com.smashing.app.core.security

interface CryptoInterface {
    /**
     * Encrypts [data] and returns a single Base64-encoded string containing iv + cipherText.
     * Caller can store this string as-is (e.g. in DataStore).
     */
    suspend fun encrypt(data: String): String

    /**
     * Decrypts a Base64-encoded blob produced by [encrypt].
     * Returns the decrypted string, or null if decoding/decryption fails.
     */
    suspend fun decrypt(encodedBlob: String): String?
}
