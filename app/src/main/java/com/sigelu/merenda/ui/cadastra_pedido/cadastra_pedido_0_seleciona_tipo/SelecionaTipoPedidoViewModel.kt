package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.merenda.App
import com.sigelu.merenda.common.domain.interactors.CadastraPedidoModel
import com.sigelu.merenda.common.domain.interactors.Fluxo
import com.sigelu.merenda.common.domain.model.TipoMovimento
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra.SelecionaObraActivity
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity

class SelecionaTipoPedidoViewModel (private val controller: CadastraPedidoModel): ViewModel() {

    private var tipoMovimento: TipoMovimento = TipoMovimento.ALMOXARIFADO_PARA_NUCLEO
    private var proximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

    fun proximaTela() = proximaTela
    fun setProximaTelaUndefined() { proximaTela.value  = Response.loading() }

    fun selecionaTipoPedido(pos: Int){

        tipoMovimento = if(pos == 0)
            TipoMovimento.ALMOXARIFADO_PARA_NUCLEO
        else
            TipoMovimento.ALMOXARIFADO_PARA_OBRA
    }

    fun confirmaDestinoPedido() {

        proximaTela.value = Response.loading()

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