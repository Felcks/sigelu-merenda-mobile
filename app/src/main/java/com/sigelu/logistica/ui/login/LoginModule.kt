package com.sigelu.logistica.ui.login

import com.sigelu.logistica.common.domain.interactors.LoginController
import com.sigelu.logistica.common.domain.repository.PermissaoSistemaRepository
import com.sigelu.logistica.common.domain.repository.LoginRepository
import com.sigelu.logistica.common.domain.repository.UsuarioRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on May, 2019
 */

@Module
class LoginModule {

    @Provides
    fun provideViewModelFactory(controller: LoginController): LoginViewModelFactory {
        return LoginViewModelFactory(controller)
    }

    @Provides
    fun provideLoginRepo(): LoginRepository {
        return LoginRepository()
    }

    @Provides
    fun provideCarregaPermissoesSistema(): PermissaoSistemaRepository {
        return PermissaoSistemaRepository()
    }

    @Provides
    fun provideUsuarioRepository(): UsuarioRepository {
        return UsuarioRepository()
    }
}