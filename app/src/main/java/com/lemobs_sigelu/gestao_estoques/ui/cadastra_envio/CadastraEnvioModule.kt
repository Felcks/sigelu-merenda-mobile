package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_informacoes_basicas.CadastraEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_item_envio.CadastraItemEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.confirma_cadastro_envio.ConfirmaCadastroEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.seleciona_item_envio.SelecionaItemEnvioViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class CadastraEnvioModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraEnvioController): CadastraEnvioViewModelFactory {
        return CadastraEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideViewModelFactory2(controller: CadastraEnvioController): ConfirmaCadastroEnvioViewModelFactory {
        return ConfirmaCadastroEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideViewModelFactory3(controller: CadastraEnvioController): CadastraItemEnvioViewModelFactory {
        return CadastraItemEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideViewModelFactory4(controller: CadastraEnvioController): SelecionaItemEnvioViewModelFactory {
        return SelecionaItemEnvioViewModelFactory(controller)
    }


    @Provides
    fun provideEnvioRepository(): EnvioRepository{
        return EnvioRepository()
    }

    @Provides
    fun providePedidoRepository(): PedidoRepository {
        return PedidoRepository()
    }

    @Provides
    fun provideItemPedidoRepository(): ItemPedidoRepository{
        return ItemPedidoRepository()
    }
}