package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.MATERIAL_DE_PEDIDO_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.materiaisDePedido
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

class SelecionaMaterialParaPedidoRepository {

    fun selecionaMaterial(context: Context, id: Int): Boolean{

        val material = MATERIAL_DE_PEDIDO_MOCKADOS[id]
        if(!materiaisDePedido.contains(material)){
            materiaisDePedido.add(material)
            FlowSharedPreferences.setMaterialAdicionadoId(context, id)
            return true
        }
        else{
            return false
        }
    }
}