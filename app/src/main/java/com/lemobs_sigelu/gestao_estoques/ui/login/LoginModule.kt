package com.lemobs_sigelu.gestao_estoques.ui.login

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.LoginUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaPermissoesSistemasRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciadorCredenciaisRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on May, 2019
 */

@Module
class LoginModule {

    @Provides
    fun provideLoginRepo(): LoginRepository {
        return LoginRepository()
    }

    @Provides
    fun provideCarregaPermissoesSistema(): CarregaPermissoesSistemasRepository {
        return CarregaPermissoesSistemasRepository()
    }

    @Provides
    fun provideGerenciaCredenciais(): GerenciadorCredenciaisRepository {
        return GerenciadorCredenciaisRepository()
    }

    @Provides
    fun provideViewModelFactory(useCase: LoginUseCase): LoginViewModelFactory {
        return LoginViewModelFactory(useCase)
    }
}