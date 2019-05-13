package com.lemobs_sigelu.gestao_estoques.ui.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.LoginUseCase

class LoginViewModelFactory(val useCase: LoginUseCase): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}