package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.Fluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoMovimento
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.MovimentoInvalidoException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra.SelecionaObraActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity
import java.lang.Exception

class SelecionaTipoPedidoViewModel (private val controller: CadastraPedidoModel): ViewModel() {

    private var tipoMovimento: TipoMovimento = TipoMovimento.ALMOXARIFADO_PARA_NUCLEO
    private var proximaTela = MutableLiveData<Response>()

    fun proximaTela() = proximaTela

    fun selecionaTipoPedido(pos: Int){

        tipoMovimento = if(pos == 0)
            TipoMovimento.ALMOXARIFADO_PARA_NUCLEO
        else
            TipoMovimento.ALMOXARIFADO_PARA_OBRA
    }

    fun confirmaDestinoPedido() {

        if(tipoMovimento == TipoMovimento.ALMOXARIFADO_PARA_NUCLEO){

            controller.iniciaRMParaEstoque()
            proximaTela.value = Response.success(Intent(App.instance, SelecionaItemPedidoParaNucleoActivity::class.java))
        }
        else if(tipoMovimento == TipoMovimento.ALMOXARIFADO_PARA_OBRA){

            //Não inicia RM pois a obra ainda não foi definida
            proximaTela.value = Response.success(Intent(App.instance, SelecionaObraActivity::class.java))
        }
    }

    fun getFluxo(): Fluxo {
        return controller
    }
}