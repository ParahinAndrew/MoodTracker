package com.example.moodtracker.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.R
import com.example.moodtracker.biometric.BiometricAuthCallback
import com.example.moodtracker.biometric.BiometricAuthManager
import com.example.moodtracker.databinding.ActivityMainBinding
import com.example.moodtracker.databinding.ScreenPasswordBinding
import com.example.moodtracker.databinding.ScreenRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BiometricAuthCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingPassword: ScreenPasswordBinding
    private lateinit var bindingRegistration: ScreenRegistrationBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var biometricAuthManager: BiometricAuthManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingPassword = ScreenPasswordBinding.inflate(layoutInflater)
        bindingRegistration = ScreenRegistrationBinding.inflate(layoutInflater)

        bottomNavigate()


        biometricAuthManager = BiometricAuthManager(this, this)

        if (viewModel.username.value != null) {

            setContentView(bindingPassword.root)

            bindingPassword.textLoggin.text = "Are you ${viewModel.username.value}?"

            // Проверка доступности биометрических данных при запуске
            if (biometricAuthManager.isBiometricAvailable()) {
                bindingPassword.fingerprintIcon.visibility = View.VISIBLE
                biometricAuthManager.startAuthentication()

                Log.d("MainActivity", "Biometric authentication started")
            } else {
                Toast.makeText(this, "Biometric authentication is not available", Toast.LENGTH_SHORT).show()
                Log.d("MainActivity", "Biometric authentication is not available")
            }

            bindingPassword.authenticateBtn.setOnClickListener {
                checkPassword()
            }

            bindingPassword.fingerprintIcon.setOnClickListener {
                if (biometricAuthManager.isBiometricAvailable()) {
                    biometricAuthManager.startAuthentication()
                    Log.d("MainActivity", "Biometric authentication started by icon click")
                } else {
                    Toast.makeText(this, "Biometric authentication is not available", Toast.LENGTH_SHORT).show()
                    Log.d("MainActivity", "Biometric authentication is not available by icon click")
                }
            }
        } else {
            setContentView(bindingRegistration.root)
            bindingRegistration.authenticateBtn.setOnClickListener {
                registration()
            }
        }
    }

    private fun bottomNavigate() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        binding.navMenu.setOnItemSelectedListener {
            navHostFragment.findNavController().navigate(it.itemId)
            true
        }

        binding.newMoodBtn.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.action_homeFragment_to_newMoodFragment)
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newMoodFragment -> {
                    binding.navMenu.visibility = View.GONE
                    binding.newMoodBtn.visibility = View.GONE
                }
                else -> {
                    binding.navMenu.visibility = View.VISIBLE
                    binding.newMoodBtn.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        bindingPassword.inputPassword.text = null
        bindingPassword.textError.visibility = View.INVISIBLE
        setContentView(bindingPassword.root)
    }

    private fun registration() {
        val passwordText = bindingRegistration.inputPassword
        val confirmPassText = bindingRegistration.checkPassword

        if (passwordText.text.toString() != confirmPassText.text.toString()) {
            // Установка цвета обводки в красный, если пароли не совпадают
            bindingRegistration.inputPasswordLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.deep_red_orange)
            bindingRegistration.inputCheckPasswordLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.deep_red_orange)
        } else {
            bindingRegistration.inputPasswordLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.white)
            bindingRegistration.inputCheckPasswordLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.white)
            viewModel.saveUserData(bindingRegistration.inputName.text.toString(), bindingRegistration.inputPassword.text.toString())
            setContentView(binding.root)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkPassword() {
        val password = bindingPassword.inputPassword

        if (password.text.toString() == viewModel.password.value) {
            setContentView(binding.root)
        } else {
            // Установка цвета обводки в красный
            bindingPassword.inputPasswordLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.deep_red_orange)
            bindingPassword.textError.visibility = View.VISIBLE
        }



    }

    override fun onAuthenticationSuccess() {
        Toast.makeText(this, "Authentication succeeded", Toast.LENGTH_SHORT).show()
        setContentView(binding.root)
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        Toast.makeText(this, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
    }
}