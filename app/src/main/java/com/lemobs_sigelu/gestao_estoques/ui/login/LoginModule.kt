package com.lemobs_sigelu.gestao_estoques.ui.login

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.LoginController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PermissaoSistemaRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.LoginRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.UsuarioRepository
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