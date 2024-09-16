package com.example.moodtracker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.sharedpreference.SharedPreferencesManager
import com.example.domain.models.MoodCardDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> = _username

    private val _password = MutableLiveData<String?>()
    val password: LiveData<String?> = _password

    private val _isDataValid = MutableLiveData<Boolean>()
    val isDataValid: LiveData<Boolean> = _isDataValid

    init {
        // Инициализация данных из SharedPreferences
        _username.value = sharedPreferencesManager.getUsername()
        _password.value = sharedPreferencesManager.getPassword()
    }

    // Метод для сохранения данных пользователя
    fun saveUserData(username: String, password: String) {
        viewModelScope.launch {
            sharedPreferencesManager.saveUserData(username, password)
        }
    }




    // Метод для обновления имени пользователя
    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    // Метод для обновления пароля
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }


    // Метод для сравнения введенных данных с сохраненными
    fun validateUserData(enteredUsername: String, enteredPassword: String) {
        val savedUsername = sharedPreferencesManager.getUsername()
        val savedPassword = sharedPreferencesManager.getPassword()

        _isDataValid.value = enteredUsername == savedUsername && enteredPassword == savedPassword
    }

}