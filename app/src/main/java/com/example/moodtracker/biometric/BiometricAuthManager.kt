package com.example.moodtracker.biometric

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

const val REQUEST_CODE = 1001

class BiometricAuthManager(private val context: Context, private val callback: BiometricAuthCallback) {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var maxBiometricLevel: Int = BiometricManager.Authenticators.DEVICE_CREDENTIAL

    init {
        maxBiometricLevel = checkMaxBiometricLevel(context)
        setupBiometricPrompt()
    }

    private fun setupBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(context)

        biometricPrompt = BiometricPrompt(context as FragmentActivity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                callback.onAuthenticationSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                callback.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback.onAuthenticationFailed()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use password")
            .build()

        Log.d("BiometricAuthManager", "BiometricPrompt initialized")
    }

    fun startAuthentication() {
        Log.d("BiometricAuthManager", "Starting biometric authentication")
        biometricPrompt.authenticate(promptInfo)
    }

    fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        val result = biometricManager.canAuthenticate(maxBiometricLevel)
        Log.d("BiometricAuthManager", "Biometric availability result: $result")
        return when (result) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    private fun checkMaxBiometricLevel(context: Context): Int {
        val biometricManager = BiometricManager.from(context)

        // Проверка BIOMETRIC_STRONG
        var result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (result == BiometricManager.BIOMETRIC_SUCCESS) {
            Log.d("MY_APP_TAG", "BIOMETRIC_STRONG is available.")
            return BiometricManager.Authenticators.BIOMETRIC_STRONG
        }

        // Проверка BIOMETRIC_WEAK
        result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        if (result == BiometricManager.BIOMETRIC_SUCCESS) {
            Log.d("MY_APP_TAG", "BIOMETRIC_WEAK is available.")
            return BiometricManager.Authenticators.BIOMETRIC_WEAK
        }

        // Проверка DEVICE_CREDENTIAL
        result = biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        if (result == BiometricManager.BIOMETRIC_SUCCESS) {
            Log.d("MY_APP_TAG", "DEVICE_CREDENTIAL is available.")
            return BiometricManager.Authenticators.DEVICE_CREDENTIAL
        }

        // Если ни один из уровней не доступен, возвращаем DEVICE_CREDENTIAL
        Log.e("MY_APP_TAG", "No biometric features available on this device.")
        return BiometricManager.Authenticators.DEVICE_CREDENTIAL
    }
}