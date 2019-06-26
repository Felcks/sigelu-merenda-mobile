package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import com.lemobs_sigelu.gestao_estoques.extensions_constants.createdAtToDate
import io.reactivex.Observable

class CarregaPedidoRepository {

    val api = RestApi()
}