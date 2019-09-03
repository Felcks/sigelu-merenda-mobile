package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra.SelecionaObraActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo.CadastraNucleoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_3_obra.CadastraObraActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_3_obra.CadastraObraViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import java.lang.Exception

class SelecionaTipoPedidoViewModel (private val controller: ICadastraPedidoController): ViewModel() {

    init {
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
    }

    fun selecionaTipoPedido(tipoPedido: TipoPedido){
        controller.selecionaTipoPedido(tipoPedido)
    }

    fun confirmaDestinoDePedido(): Intent {

        val tipoPedido = controller.getTipoPedidoSelecionado()

        if(tipoPedido == TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO){

            /* O id do local de origem não foi definido ainda */
            val origem = Local(8, "Almoxarifado", "Almoxarifado")
            val destino = Local(AppSharedPreferences.getNucleoID(App.instance), "Núcleo", AppSharedPreferences.getNucleoNome(App.instance))
            controller.confirmaDestinoDePedido(origem, destino)

            return Intent(App.instance, SelecionaItemPedidoParaNucleoActivity::class.java)
        }
        else if(tipoPedido == TipoPedido.FORNECEDOR_PARA_OBRA){

            return Intent(App.instance, SelecionaObraActivity::class.java)
        }


        throw Exception("Tipo de pedido não permitido")
    }
}