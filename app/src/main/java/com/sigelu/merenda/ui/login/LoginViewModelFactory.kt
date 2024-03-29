package com.sigelu.merenda.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sigelu.merenda.common.domain.interactors.LoginController

class LoginViewModelFactory(val controller: LoginController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}