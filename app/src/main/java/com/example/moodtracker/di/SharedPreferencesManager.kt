package com.example.moodtracker.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val PREF_NAME = "MoodTrackerPrefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
    }

    private var sharedPreferences: SharedPreferences


    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            PREF_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveUserData(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun getUsername(): String? = sharedPreferences.getString(KEY_USERNAME, null)

    fun getPassword(): String? = sharedPreferences.getString(KEY_PASSWORD, null)



    fun updateUsername(newUsername: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, newUsername)
        editor.apply()
    }

    fun updatePassword(newPassword: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_PASSWORD, newPassword)
        editor.apply()
    }

    fun clearUserData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}