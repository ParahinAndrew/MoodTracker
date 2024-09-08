package com.example.moodtracker.biometric

interface BiometricAuthCallback {
    fun onAuthenticationSuccess()
    fun onAuthenticationError(errorCode: Int, errString: CharSequence)
    fun onAuthenticationFailed()
}