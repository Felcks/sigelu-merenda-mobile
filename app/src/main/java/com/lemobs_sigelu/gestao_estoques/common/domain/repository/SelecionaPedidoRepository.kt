package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

class SelecionaPedidoRepository {

    fun armazenaPedidoNoFluxo(context: Context, id: Int) {

        FlowSharedPreferences.setPedidoId(context, id)
    }
}