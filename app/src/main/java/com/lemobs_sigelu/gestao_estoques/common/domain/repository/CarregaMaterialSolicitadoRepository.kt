package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.MATERIAIS_PARA_CADASTRO_OBRA
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences

class CarregaMaterialSolicitadoRepository {

    fun carregaMaterial(context: Context): MaterialParaCadastro {

        val materialId = CadastraPedidoSharedPreferences.getMaterialAdicionadoId(context)
        return MATERIAIS_PARA_CADASTRO_OBRA[materialId]
    }
}