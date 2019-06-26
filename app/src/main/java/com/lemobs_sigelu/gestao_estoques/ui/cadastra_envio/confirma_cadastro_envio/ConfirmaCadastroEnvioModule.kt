package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.confirma_cadastro_envio

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import dagger.Module
import dagger.Provides


@Module
class ConfirmaCadastroEnvioModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraEnvioController): ConfirmaCadastroEnvioViewModelFactory{
        return ConfirmaCadastroEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideEnvioRepository(): EnvioRepository{
        return EnvioRepository()
    }

    @Provides
    fun providePedidoRepository(): PedidoRepository{
        return PedidoRepository()
    }
}