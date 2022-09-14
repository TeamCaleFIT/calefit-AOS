package com.example.calefit.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    val userEmail = MutableStateFlow(DEFAULT_VALUE)
    val userPassword = MutableStateFlow(DEFAULT_VALUE)
    val userPasswordCheck = MutableStateFlow(DEFAULT_VALUE)
    val userNickName = MutableStateFlow(DEFAULT_VALUE)

    val hasRequiredSignUpData: StateFlow<Boolean> =
        combine(userEmail, userPassword, userPasswordCheck, userNickName) { _ ->
            checkValidateForm()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DEFAULT_REGISTER_VALUE)

    private fun checkValidateForm(): Boolean {
        return (emailValidation.matches(userEmail.value)
                && userPassword.value.isNotEmpty()
                && userPasswordCheck.value.isNotEmpty()
                && userPassword.value == userPasswordCheck.value
                && userNickName.value.isNotEmpty())
    }

    fun checkPassword(): Boolean = userPassword.value == userPasswordCheck.value

    fun checkEmailAddress(): Boolean =
        (emailValidation.matches(userEmail.value) || userEmail.value.isEmpty())

    companion object {
        private const val DEFAULT_VALUE = ""
        private const val DEFAULT_REGISTER_VALUE = false
        private val emailValidation =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex()
    }
}
